//
//  XMLClient.m
//
//  Created by William Hamilton on 1/7/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "XMLClient.h"

NSString * const OODSS_CLIENT = @"OODSS_CLIENT";

@interface XMLClient ()

@property(nonatomic, readwrite, retain) NSMutableDictionary* headerMap;
@property(nonatomic, readwrite, retain) NSMutableData* incomingMessageBuffer;
@property(nonatomic, readwrite, retain) NSMutableData* currentKeyHeaderSequence;
@property(nonatomic, readwrite, retain) NSMutableData* currentHeaderSequence;
@property(nonatomic, readwrite, retain) NSMutableData* firstMessageBuffer;
@property(nonatomic, readwrite, retain) NSMutableString* headerConstructionString;
@property(nonatomic, readwrite, retain) NSMutableString* messageConstructionString;
@property(nonatomic, readwrite, retain) NSMutableData* outgoingMessageBuffer;
@property(nonatomic, readwrite, retain) NSString* contentCoding;
@property(nonatomic, readwrite, copy) NSString* sessionId;
@property(nonatomic, readwrite, retain) Scope* scope;
@property(nonatomic, readwrite, retain) TranslationScope* translationScope;

@property(nonatomic, readwrite, assign) int endOfFirstHeader;
@property(nonatomic, readwrite, assign) int startReadIndex;
@property(nonatomic, readwrite, assign) int contentLengthRemaining;
@property(nonatomic, readwrite, assign) int uidOfCurrentMessage;
@property(nonatomic, readwrite, assign) int currentUID;

- (int) parseHeaderAtIndex:(int) startIndex andBuffer:(NSData*) buffer;
- (void) processIncomingMessage:(NSMutableData*) message withUID:(int) uid;
- (void) clean;

@end

@implementation XMLClient

@synthesize headerMap;
@synthesize incomingMessageBuffer;
@synthesize currentKeyHeaderSequence;
@synthesize currentHeaderSequence;
@synthesize firstMessageBuffer;
@synthesize headerConstructionString;
@synthesize messageConstructionString;
@synthesize outgoingMessageBuffer;
@synthesize contentCoding;
@synthesize sessionId;
@synthesize delegate;
@synthesize scope;
@synthesize translationScope;
@synthesize endOfFirstHeader, startReadIndex, contentLengthRemaining, uidOfCurrentMessage, currentUID;

#pragma mark ConnectionDelegate methods

- (void) connectionAttemptFailed:(Connection*) connection
{
  [self clean];
  [delegate connectionAttemptFailed:self];
}

- (void) connectionTerminated:(Connection*) connection
{
  [self clean];
  [delegate connectionTerminated:self];
}

- (void) clean
{
  endOfFirstHeader = -1;
  startReadIndex = 0;
  contentLengthRemaining = -1;
  uidOfCurrentMessage = 0;
  currentUID = 1;
  
  [headerMap removeAllObjects];
  [incomingMessageBuffer setLength:0];
  [currentKeyHeaderSequence setLength:0];
  [currentHeaderSequence setLength:0];
  [firstMessageBuffer setLength:0];
  [outgoingMessageBuffer setLength:0];
  
  
  NSRange deleteRange;
  deleteRange.length = [headerConstructionString length];
  deleteRange.location = 0;
  [headerConstructionString deleteCharactersInRange:deleteRange];
}

- (int) receivedNetworkData:(NSData*) incomingData viaConnection:(Connection*) connection
{
  [incomingMessageBuffer appendBytes:[incomingData bytes] length:[incomingData length]];
  
  /* Process as many messages as possible */
  while([incomingMessageBuffer length] > 0)
  {
    if(endOfFirstHeader == -1)
    {
      endOfFirstHeader = [self parseHeaderAtIndex:startReadIndex andBuffer: incomingMessageBuffer];
    }
    if(endOfFirstHeader == -1)
    {
      /* no header yet, might check for too large at some point */
      startReadIndex = [incomingMessageBuffer length];
      break;
    }
    else
    {
      /*
        we've read all of the header and have it loaded into the
        dictionary; now we can use it
       */
      if(contentLengthRemaining == -1)
      {
        contentLengthRemaining = [((NSString*) [headerMap objectForKey:CONTENT_LENGTH_STRING]) integerValue];
        if(contentLengthRemaining == 0)
        {
          /* This is really bad not sure how to do error handling just yet.*/
        }
        
        uidOfCurrentMessage = [((NSString*) [headerMap objectForKey:UNIQUE_IDENTIFIER_STRING]) integerValue];
        if(contentLengthRemaining == 0)
        {
          /* This is really bad not sure how to do error handling just yet.*/
        }
        
        [contentCoding release];
        contentCoding = ((NSString*) [headerMap objectForKey:HTTP_CONTENT_CODING]);
        NSRange rangeToDelete;
        rangeToDelete.location = 0;
        rangeToDelete.length = endOfFirstHeader;
        
        [incomingMessageBuffer replaceBytesInRange:rangeToDelete withBytes:NULL length:0];
        [headerMap removeAllObjects];
        startReadIndex = 0;
      }
    }
    
    /*
     *We have the end of the first header (otherwise we would have
     *broken out earlier). If we don't have the content length,
     *something bad happended, because it should have been read.
     */
    if(contentLengthRemaining == -1)
    {
      /*
       * if we still don't have the remaining length, then there was a prob
       */
      break;
    }
    // see if buffer has enough characters to
    // include the specified content length
    if([incomingMessageBuffer length] >= contentLengthRemaining)
    {
      [firstMessageBuffer appendBytes:[incomingMessageBuffer mutableBytes] length:contentLengthRemaining];
      
      NSRange rangeToDelete;
      rangeToDelete.location = 0;
      rangeToDelete.length = contentLengthRemaining;
      [incomingMessageBuffer replaceBytesInRange:rangeToDelete withBytes:NULL length:0];
      
      contentLengthRemaining = -1;
      endOfFirstHeader = -1;
    }
    else
    {
      [firstMessageBuffer appendBytes:[incomingMessageBuffer mutableBytes] length:[incomingMessageBuffer length]];
      //need to get more from buffer in next itteration
      contentLengthRemaining -= [incomingMessageBuffer length];
      [incomingMessageBuffer setLength:0];
    }
    
    if([firstMessageBuffer length] > 0 && contentLengthRemaining == -1)
    {
      //we've got an incoming message (Response)
      
      [self processIncomingMessage:firstMessageBuffer withUID:uidOfCurrentMessage];
      [firstMessageBuffer setLength:0];
    }
  };

  //we process all of the data
  return [incomingData length];
}

- (void) processIncomingMessage:(NSMutableData*) message withUID:(int) uid
{
  NSString* messageString = [[NSString alloc] initWithBytes:[message bytes] 
                                                       length:[message length]
                                                       encoding:NSISOLatin1StringEncoding];
  
  NSLog(messageString);
  
  ElementState* incomingMessage = [ElementState translateFromXMLData:message translationScope:self.translationScope];
 
  if([incomingMessage isKindOfClass:[ResponseMessage class]])
  {
    ResponseMessage* responseMessage = (ResponseMessage*) incomingMessage;
    
    if([responseMessage isKindOfClass:[InitConnectionResponse class]])
    {
      InitConnectionResponse* initConnResp = (InitConnectionResponse*) incomingMessage;
      self.sessionId = initConnResp.sessionId;
      [delegate connectionSuccessful:self withSessionId:self.sessionId];
    }
    
    [responseMessage processResponse: self.scope];
  }
  else if ([incomingMessage isKindOfClass: [UpdateMessage class]])
  {
    UpdateMessage* updateMessage = (UpdateMessage*) incomingMessage;
    
    [updateMessage processUpdate: self.scope];
  }
  
  [incomingMessage release];
}
  
- (int) parseHeaderAtIndex:(int) startIndex andBuffer:(NSData*) buffer
{
  char currentChar = 0;
  const char* bytes = [buffer bytes];
  bool maybeEndSequence = FALSE;
    
  for(int i = 0; i < [buffer length]; i++)
  {
    currentChar = bytes[i];
    switch(currentChar)
    {
        case ':':
          [currentKeyHeaderSequence appendBytes:[currentHeaderSequence mutableBytes] length:[currentHeaderSequence length]];
          [currentHeaderSequence setLength:0];
          break;
        case '\r':
          if(bytes[i+1] == '\n')
          {
            if(!maybeEndSequence)
            {
              /*encode key and value into NSStrings and put them in the headerMap*/
              NSString* currentSequenceString = [[NSString alloc] initWithBytes:[currentHeaderSequence mutableBytes] 
                                                                         length:[currentHeaderSequence length] encoding: NSISOLatin1StringEncoding];
              NSString* currentKeySequenceString = [[NSString alloc] initWithBytes:[currentKeyHeaderSequence mutableBytes]
                                                                            length:[currentKeyHeaderSequence length] encoding: NSISOLatin1StringEncoding];
              [headerMap setObject: currentSequenceString forKey:currentKeySequenceString];
              
              [currentHeaderSequence setLength:0];
              [currentKeyHeaderSequence setLength:0];
              
              [currentKeySequenceString release];
              [currentSequenceString release];
              i++;
            }
            else
            {
              return i + 2;
            }
            maybeEndSequence = true;
          }
          break;
        default:
          [currentHeaderSequence appendBytes:(bytes + i) length:1];
          maybeEndSequence = false;
          break;
    }
  }
  return -1;
}

- (id)initWithHostAddress:(NSString*) host andPort:(int) port andTranslationScope:(TranslationScope*) transScope
{
  self = [super init];
  scope = [[Scope alloc] init];
  NSValue* selfPointerWrapper = [NSValue valueWithPointer: self];
  [scope setObject:selfPointerWrapper forKey:OODSS_CLIENT];
  
  self.translationScope = transScope;
  
  headerMap = [[NSMutableDictionary alloc] init];
  incomingMessageBuffer = [[NSMutableData alloc] init];
  currentKeyHeaderSequence = [[NSMutableData alloc] initWithCapacity:200];
  currentHeaderSequence = [[NSMutableData alloc] initWithCapacity: 200];
  firstMessageBuffer = [[NSMutableData alloc] initWithCapacity:(1024 * 4)];
  
  headerConstructionString = [[NSMutableString alloc] initWithCapacity:100];
  messageConstructionString = [[NSMutableString alloc] initWithCapacity:(1024*4)];
  outgoingMessageBuffer = [[NSMutableData alloc] initWithCapacity:(1024 *4)];
  sessionId = nil;
  
  theConnection = [[Connection alloc] initWithHostAddress:host andPort: port];
  
  theConnection.delegate = self;
  
  [self clean];
  
  return self;
}
  
- (void) sendMessage:(RequestMessage*) request
{
  //clear message construction buffer
  NSRange rangeToDelete;
  rangeToDelete.location = 0;
  rangeToDelete.length = [messageConstructionString length];
  [messageConstructionString deleteCharactersInRange:rangeToDelete];
  
  //translate message
  [request translateToXML:messageConstructionString];
  //get message length before encoding
  int messageLengthWhenEncoded = [messageConstructionString lengthOfBytesUsingEncoding:NSISOLatin1StringEncoding];
  
  int uid = currentUID++;
  
  // construct header
  rangeToDelete.length = [headerConstructionString length];
  [headerConstructionString deleteCharactersInRange:rangeToDelete];
  [headerConstructionString appendFormat:@"%@:%d%@" , CONTENT_LENGTH_STRING, messageLengthWhenEncoded, HTTP_HEADER_LINE_DELIMETER];
  [headerConstructionString appendFormat:@"%@:%d%@" , UNIQUE_IDENTIFIER_STRING, uid, HTTP_HEADER_TERMINATOR];
  
  //get header length before encoding
  int headerLengthWhenEncoded = [headerConstructionString lengthOfBytesUsingEncoding:NSISOLatin1StringEncoding];
  
  //prep the buffer for filling with the message
  [outgoingMessageBuffer setLength:(messageLengthWhenEncoded + headerLengthWhenEncoded)];
  void* bufferPointer = [outgoingMessageBuffer mutableBytes];
  
  //copy over the header into the buffer
  NSUInteger usedBytes;
  NSRange rangeToCopy = NSMakeRange(0, [headerConstructionString length]);
  [headerConstructionString getBytes:bufferPointer maxLength:headerLengthWhenEncoded
                            usedLength:(&usedBytes) encoding:NSISOLatin1StringEncoding
                            options:NSStringEncodingConversionAllowLossy range:rangeToCopy remainingRange:NULL];
  
  
  //copy over the serializedMessage into the buffer
  rangeToCopy = NSMakeRange(0, [messageConstructionString length]);
  [messageConstructionString getBytes:(bufferPointer + headerLengthWhenEncoded) maxLength:messageLengthWhenEncoded
                     usedLength:(&usedBytes) encoding:NSISOLatin1StringEncoding
                     options:NSStringEncodingConversionAllowLossy range:rangeToCopy remainingRange:NULL];
  
  [theConnection sendData:outgoingMessageBuffer];
}
  
-(void) connect
{
  [theConnection connect];
  
  InitConnectionRequest* initReq = [InitConnectionRequest alloc];
  initReq.sessionId = self.sessionId;
  
  [self sendMessage:initReq];
  
  [initReq release];
}

-(void) disconnect
{
  DisconnectRequest* request = [[DisconnectRequest alloc] init];
  [self sendMessage:request];
  
  [theConnection close];
  [delegate connectionTerminated:self];
}
-(void) dealloc
{
  [scope release];
  [headerMap release];
  [incomingMessageBuffer release];
  [currentKeyHeaderSequence release];
  [currentHeaderSequence release];
  [firstMessageBuffer release];
  [headerConstructionString release];
  [outgoingMessageBuffer release];
  [sessionId release];
  [theConnection release];
  //[translationScope release];
  
  [super dealloc];
}
@end
