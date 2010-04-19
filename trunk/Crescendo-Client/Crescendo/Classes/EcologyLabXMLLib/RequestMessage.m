//
//  RequestMessage.m
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "RequestMessage.h"


@implementation RequestMessage

-(ResponseMessage*) PerformService:(Scope*) scope
{
  [self doesNotRecognizeSelector:_cmd];
  return nil;
}

@end
