//
//  HistoryEchoResponse.m
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "HistoryEchoResponse.h"
#import "XMLClient.h"

@implementation HistoryEchoResponse

@synthesize echo;
@synthesize prevEcho;

-(void) processResponse:(Scope*) scope
{
  XMLClient* client = (XMLClient*)[((NSValue*) [scope objectForKey:OODSS_CLIENT]) pointerValue];
  
  int nextEcho = [self.echo intValue];
  
  nextEcho++;
  
  HistoryEchoRequest* req = [[HistoryEchoRequest alloc] init];
  
  req.newEcho = [NSString stringWithFormat:@"%d", nextEcho];
  
  [client performSelector:@selector(sendMessage:) withObject: req afterDelay:0.25];
  
  [req release];
}

@end
