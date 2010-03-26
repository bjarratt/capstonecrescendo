//
//  Type.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <stdio.h>
#import <objc/runtime.h>
#import	<objc/message.h>
#import <Foundation/Foundation.h>

@class FieldDescriptor;
@class ElementState;

@protocol Type

- (id)initWithString : (NSString *)value;
- (id) getValueFromString: (NSString *) value;
- (id) getInstance;
- (void) setField: (id) object fieldName: (const char *) fn;
- (void) setField: (id) object fieldName: (NSString *) fn value: (id) value;
- (void) appendValue: (NSMutableString *) buffy fieldDescriptor: (FieldDescriptor *) fd context: (id) context;
- (void) appendValue: (NSMutableString *) buffy context: (id) context;
- (BOOL) isDefaultValue: (FieldDescriptor *) fieldDescriptor context: (ElementState *) elementState;

@end