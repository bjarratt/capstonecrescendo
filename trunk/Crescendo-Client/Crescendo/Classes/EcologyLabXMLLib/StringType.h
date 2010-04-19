//
//  StringType.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <stdio.h>
#import <objc/runtime.h>
#import	<objc/message.h>
#import <Foundation/Foundation.h>
#import "ReferenceType.h"

@interface StringType : ReferenceType {
}

+ (id) stringTypeWithString: (NSString *) value;

@end