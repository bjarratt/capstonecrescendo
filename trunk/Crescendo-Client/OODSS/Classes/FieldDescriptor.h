//
//  FieldDescriptor.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//


#import <stdio.h>
#import <objc/runtime.h>
#import <Foundation/Foundation.h>

#import "Type.h"
#import "fTypes.h"

#define START_CDATA     "<![CDATA["
#define END_CDATA       "]]>"

@class ClassDescriptor;
@class ElementState;

@interface FieldDescriptor : NSObject
{
	Ivar *field;
	Class *elementClass;
	
	NSString *tagName;
	NSMutableArray *otherTags;

	
	int type;
	id <Type> scalarType;
	bool isCDATA;
	bool needsEscaping;
	bool isWrapped;

	NSString *collectionOrMapTagName;
	NSMutableDictionary *tagClassDescriptors;
	NSMutableDictionary *tagClasses;
	
	ClassDescriptor *declaringClassDescriptor;
	FieldDescriptor *wrapperFD;
}

@property (nonatomic, readwrite) Ivar *field;
@property (nonatomic, readwrite) Class *elementClass;
@property (nonatomic, readwrite, retain) NSString *tagName;
@property (nonatomic, readwrite, retain) NSString *collectionOrMapTagName;
@property (nonatomic, readwrite, retain) NSMutableArray *otherTags;
@property (nonatomic, readwrite, retain) NSMutableDictionary *tagClasses;
@property (nonatomic, readwrite, retain) ClassDescriptor *declaringClassDescriptor;
@property (nonatomic, readwrite, retain) id <Type> scalarType;
@property (nonatomic, readwrite, retain) FieldDescriptor *wrapperFD;
@property (nonatomic, readwrite, retain) NSMutableDictionary *tagClassDescriptors;
@property (nonatomic, readwrite) int type;
@property (nonatomic, readwrite) bool isCDATA;
@property (nonatomic, readwrite) bool needsEscaping;
@property (nonatomic, readwrite) bool isWrapped;

+ (id) fieldDescriptor;
+ (id) fieldDescritorWithClassDescriptor: (ClassDescriptor *) classDescriptor;
+ (id) ignoredElementFieldDescriptor;
+ (id) fieldDescriptorWithTagName: (NSString *) elementName;
+ (id) fieldDescriptorWrapped: (ClassDescriptor *) classDescriptor fieldDescriptor: (FieldDescriptor *) fieldDescriptor wrapperTag: (NSString *) wrapperTag;

- (id) initWithClassDescriptor: (ClassDescriptor *) classDescriptor;
- (id) initWithTagName: (NSString *) elementName;
- (id) initWrapped: (ClassDescriptor *) classDescriptor fieldDescriptor: (FieldDescriptor *) fieldDescriptor wrapperTag: (NSString *) wrapperTag;
- (id) init;

- (id) getWrappedFieldDescriptor;

- (void) setTypeWithReference: (int *) t;
- (void) setIsCDATAWithReference: (bool *) isCData;
- (void) setNeedsEscapingWithReference: (bool *) nEscaping;
- (void) setIsWrappedWithReference: (bool *) wrapped;

- (void) writeElementStart: (NSMutableString *) output;

- (void) appendValueAsAttribute: (NSMutableString *) output elementState: (ElementState *) elementState;

- (void) setFieldToNestedObject: (ElementState *) elementState childES: (ElementState *) childElementState;
- (void) setField: (id) object value: (id) value;
- (ElementState *) constructChildElementState: (ElementState *) elementState tagName: (NSString *) elementName;

- (void) appendLeaf: (NSMutableString *) output elementState: (ElementState *) elementState;
- (void) writeWrap: (NSMutableString *) output close: (BOOL) close;
- (void) appendCollectionLeaf: (NSMutableString *) output elementState: (NSObject *) instance;

- (BOOL) isTagNameFromClassName;
- (NSString *) elementStart;
- (void) writeOpenTag: (NSMutableString *) output;
- (void) writeCloseTag: (NSMutableString *) output;
- (BOOL) isCollection;
- (BOOL) isPolymorphic;

- (void) addLeafNodeToCollection: (ElementState *) elementState leafNodeValue: (NSString *) leafNodeValue;
- (id) automaticLazyGetCollectionOrMap: (ElementState *) elementState;
- (id) getMap: (ElementState *) elementState;
- (void) addTagClass : (NSString*) name tagClass :  (Class *) tagClass;
- (void) addTagClassDescriptor : (NSString*) name tagClass :  (ClassDescriptor *) tagClassDescriptor; 

- (Ivar) getField;
- (NSString *) getFieldName;



@end