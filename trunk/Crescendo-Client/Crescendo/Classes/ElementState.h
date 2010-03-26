//
//  ElementState.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <stdio.h>
#import <objc/runtime.h>
#import	<objc/message.h>
#import <Foundation/Foundation.h>

#import "fTypes.h"
#import "XMLTools.h"

@class FieldDescriptor;
@class ClassDescriptor;
@class TranslationScope;
@class ElementStateSAXHandler;


@interface ElementState : NSObject {
	ElementState *parent;
	ClassDescriptor *classDescriptor;
	NSDictionary *elementById;
}


@property (nonatomic, readwrite, retain) ElementState *parent;
@property (nonatomic, readwrite, retain) ClassDescriptor *classDescriptor;
@property (nonatomic, readwrite, retain) NSDictionary *elementById;


- (ClassDescriptor *) classDescriptor;
- (ElementState *) parent;

- (void) translateToXML: (NSMutableString *) output fieldDescriptor: (FieldDescriptor *) fieldDescriptor;
- (void) translateToXML: (NSMutableString *) output;

+ (ElementState *) translateFromXML: (NSString *) pathToFile translationScope: (TranslationScope *) translationScope;
+ (ElementState *) translateFromXMLData: (NSData *) data translationScope: (TranslationScope *) translationScope;

- (void) setupRoot;
- (void) setupChildElementState: (ElementState *) childElementState;
- (void) translateAttributes: (TranslationScope *) translationScope withAttrib: (NSDictionary *) attributes context: (ElementState *) context;


@end