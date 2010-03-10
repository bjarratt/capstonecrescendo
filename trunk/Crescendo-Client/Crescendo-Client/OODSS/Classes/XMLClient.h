//
//  XMLClient.h
//
//  Created by William Hamilton on 1/7/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Connection.h"
#import "XMLClientDelegate.h"
#import "NetworkConstants.h"
#import "DefaultServicesTranslations.h"
#import "Scope.h"

extern NSString * const OODSS_CLIENT;

@interface XMLClient : NSObject<ConnectionDelegate> {
  id<XMLClientDelegate> delegate;
  Scope* scope;
  Connection* theConnection;
  NSString* sessionId;
  NSMutableDictionary* headerMap;
  NSMutableData* incomingMessageBuffer;
  NSMutableData* currentKeyHeaderSequence;
  NSMutableData* currentHeaderSequence;
  NSMutableData* firstMessageBuffer;
  NSMutableString* headerConstructionString;
  NSMutableString* messageConstructionString;
  NSMutableData* outgoingMessageBuffer;
  NSString* contentCoding;
  
  TranslationScope* translationScope;
  
  int endOfFirstHeader;
  int startReadIndex;
  int contentLengthRemaining;
  int uidOfCurrentMessage;
  int currentUID;
}

@property(nonatomic, readwrite, retain) id<XMLClientDelegate> delegate;
@property(nonatomic, readonly, copy) NSString* sessionId;
@property(nonatomic, readonly, retain) Scope* scope;
@property(nonatomic, readonly, retain) TranslationScope* translationScope;

- (id)initWithHostAddress:(NSString*)host andPort:(int) port andTranslationScope:(TranslationScope*) transScope;
- (void) sendMessage:(RequestMessage*) request;
- (void) connect;
- (void) disconnect;

@end
