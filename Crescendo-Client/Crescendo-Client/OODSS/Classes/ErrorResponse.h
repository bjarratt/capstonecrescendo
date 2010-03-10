//
//  ErrorResponse.h
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//
#import <Foundation/Foundation.h>
#import "ecologylabxml.h"

@interface ErrorResponse : ElementState
{
	NSString *explanation;
}

@property (nonatomic,readwrite, retain) NSString *explanation;

@end
