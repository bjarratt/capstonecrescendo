//
//  ClassDescriptor.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <stdio.h>
#import <objc/runtime.h>
#import <Foundation/Foundation.h>
#import "DictionaryList.h"
#import "XMLTools.h"

@class TranslationScope;
@class FieldDescriptor;

@interface ClassDescriptor : NSObject
{
	@private Class *describedClass;
	@private NSString *tagName;
	@private NSString *decribedClassSimpleName;
	@private NSString *describedClassPackageName;
	@private NSMutableArray *attributeFieldDescriptors;
	@private NSMutableArray *elementFieldDescriptors;
	@private DictionaryList *fieldDescriptorsByFieldName;
	@private FieldDescriptor *pseudoFieldDescriptor;
	@private NSMutableDictionary *allFieldDescriptorsByTagNames;
}

@property (nonatomic, readwrite) Class *describedClass;
@property (nonatomic, readwrite, retain) NSString *tagName;
@property (nonatomic, readwrite, retain) NSString *decribedClassSimpleName;
@property (nonatomic, readwrite, retain) NSString *describedClassPackageName;
@property (nonatomic, readwrite, retain) NSDictionary *allFieldDescriptorsByTagNames;
@property (nonatomic, readwrite, retain) NSMutableArray *attributeFieldDescriptors;
@property (nonatomic, readwrite, retain) NSMutableArray *elementFieldDescriptors;
@property (nonatomic, readwrite, retain) DictionaryList *fieldDescriptorsByFieldName;
@property (nonatomic, readwrite, retain) FieldDescriptor *pseudoFieldDescriptor;

#pragma mark ClassDescriptor - class initializer
+ (id) classDescriptor;

#pragma mark ClassDescriptor - instance functions
- (id) init;
- (id) getInstance;
- (void) addFieldDescriptor: (FieldDescriptor *) fd;
- (void) addFieldDescriptorMapping: (FieldDescriptor *) fieldDescriptor;
- (void) addFieldDescriptorMapping: (NSString*) tName fieldDescriptor : (FieldDescriptor *) fieldDescriptor; 
- (NSString *) describedClassName;
- (NSMutableArray *) attributeFieldDescriptors;
- (NSMutableArray *) elementFieldDescriptors;
- (FieldDescriptor *) pseudoFieldDescriptor;
- (FieldDescriptor *) getFieldDescriptorByTag:  (NSString *) elementName scope: (TranslationScope *) translationScope elementState: (ElementState *) elementState;

#pragma mark ClassDescriptor - static accessors
+ (id) classDescriptor: (Class) class;
+ (id) classDescriptorWithField: (Ivar) field;
+ (id) classDescriptorWithName: (NSString *) className;
+ (NSMutableDictionary *) globalClassDescriptorsMap;

@end