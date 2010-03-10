//
//  ResponseMessage.h
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//
#import <Foundation/Foundation.h>
#import "ecologylabxml.h"
#import "ServiceMessage.h"
#import "Scope.h"

@interface ResponseMessage : ServiceMessage
{
 
}

-(void) processResponse:(Scope *) scope;

@end
