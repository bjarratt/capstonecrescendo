//
//  BooleanType.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <stdio.h>
#import <objc/runtime.h>
#import	<objc/message.h>
#import <Foundation/Foundation.h>

#import "ScalarType.h"

@interface BooleanType : ScalarType {
}

+ (id) booleanTypeWithString: (NSString *) value;

@end