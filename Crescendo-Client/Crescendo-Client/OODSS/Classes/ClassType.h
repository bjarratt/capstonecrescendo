//
//  StringType.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <stdio.h>
#import <objc/objc-runtime.h>
#import <Foundation/Foundation.h>
#import "ReferenceType.h"
#import "XMLTools.h"

@interface ClassType : ReferenceType {
}

+ (id) classTypeWithString: (NSString *) value;

@end