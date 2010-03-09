//
//  XMLTools.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <stdio.h>
#import <objc/objc-runtime.h>
#import <Foundation/Foundation.h>

#import "Type.h"

@interface XMLTools : NSObject {
}

+ (NSString *) getClassSimpleName: (NSString *) classFullName;
+ (Class) getClass: (NSString *) className;
+ (const char *) getSetterFunction: (const char *) fieldName;
+ (id <Type>) typeWithString: (NSString *) value;
+ (NSString *) getTypeFromField: (Ivar) field;
+ (const char *) getCTypeFromField: (Ivar) field;
+ (id) getInstance: (Class *) getInstance;

@end