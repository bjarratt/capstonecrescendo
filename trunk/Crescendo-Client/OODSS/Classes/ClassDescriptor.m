//
//  ClassDescriptor.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "ClassDescriptor.h"
#import "TranslationScope.h"
#import "FieldDescriptor.h"

static NSMutableDictionary *globalClassDescriptorsMap;

@implementation ClassDescriptor

@synthesize describedClass;
@synthesize tagName;
@synthesize decribedClassSimpleName;
@synthesize describedClassPackageName;
@synthesize fieldDescriptorsByFieldName;
@synthesize attributeFieldDescriptors;
@synthesize elementFieldDescriptors;
@synthesize allFieldDescriptorsByTagNames; 
@synthesize pseudoFieldDescriptor; 
@synthesize pseudoFieldDescriptor;

#pragma mark ClassDescriptor - static initializer & accessors

+ (void) initialize {
	globalClassDescriptorsMap = [[NSMutableDictionary dictionary] retain];
}

+ (NSMutableDictionary *) globalClassDescriptorsMap {
	return globalClassDescriptorsMap;
}

+ (id) classDescriptor: (Class) cls {
	return [globalClassDescriptorsMap valueForKey:[NSString stringWithCString: class_getName(cls)]];
}

+ (id) classDescriptorWithField: (Ivar) field {
	return [globalClassDescriptorsMap valueForKey:[XMLTools getTypeFromField: field]];
}

+ (id) classDescriptorWithName: (NSString *) className {
	return [globalClassDescriptorsMap valueForKey: className];
}


#pragma mark ClassDescriptor - class initializer

+ (id) classDescriptor {
	return [[[ClassDescriptor alloc] init] autorelease];
}


#pragma mark ClassDescriptor - instance functions

- (id) init {
	if ( (self = [super init]) ) {
		self.fieldDescriptorsByFieldName = [NSMutableDictionary dictionary];
		self.allFieldDescriptorsByTagNames = [NSMutableDictionary dictionary];
		self.attributeFieldDescriptors = [NSMutableArray array];
		self.elementFieldDescriptors = [NSMutableArray array];
	}
	return self;
}

- (FieldDescriptor *) pseudoFieldDescriptor {
	if (pseudoFieldDescriptor == nil)
		self.pseudoFieldDescriptor = [FieldDescriptor fieldDescritorWithClassDescriptor: self];

	return pseudoFieldDescriptor;
}

- (NSMutableArray *) attributeFieldDescriptors {
	return attributeFieldDescriptors;
}

- (NSMutableArray *) elementFieldDescriptors {
	return elementFieldDescriptors;
}

- (void) addFieldDescriptor: (FieldDescriptor *) fieldDescriptor {
	
	NSString *tempTagName = [NSString stringWithString:[fieldDescriptor isCollection] ? fieldDescriptor.collectionOrMapTagName: fieldDescriptor.tagName];
	
	[fieldDescriptorsByFieldName setObject: fieldDescriptor forKey: tempTagName];
	[allFieldDescriptorsByTagNames setObject: fieldDescriptor forKey: tempTagName];

	if (fieldDescriptor.type == ATTRIBUTE)
		[attributeFieldDescriptors addObject: fieldDescriptor];
	else
		[elementFieldDescriptors addObject: fieldDescriptor];

	if ([fieldDescriptor isWrapped]) {
		FieldDescriptor *wrapper = [FieldDescriptor fieldDescriptorWrapped: self fieldDescriptor: fieldDescriptor wrapperTag:[fieldDescriptor tagName]];
		[allFieldDescriptorsByTagNames setObject: wrapper forKey:[fieldDescriptor tagName]];			
	}
}

- (NSString *) describedClassName {
	return [NSString stringWithCString: class_getName(*describedClass)];
}

- (id) getInstance {
	return [XMLTools getInstance: describedClass];
}

- (FieldDescriptor *) getFieldDescriptorByTag:  (NSString *) elementName scope: (TranslationScope *) translationScope elementState: (ElementState *) elementState {	
	return [allFieldDescriptorsByTagNames objectForKey: elementName];
}

- (void) addFieldDescriptorMapping: (FieldDescriptor *) fieldDescriptor {
	NSString *fdTagName = [fieldDescriptor tagName];
	if (fdTagName != nil) {
		id previousValue = [allFieldDescriptorsByTagNames objectForKey: fdTagName];
		[allFieldDescriptorsByTagNames setValue: fieldDescriptor forKey: fdTagName];
		if (previousValue != nil) NSLog(@"tag <%@>:\t field[%@] overrides field[%@%]", fdTagName, [fieldDescriptor getFieldName], [previousValue getFieldName]);
	}
}

- (void) addFieldDescriptorMapping: (NSString*) tName fieldDescriptor : (FieldDescriptor *) fieldDescriptor {
	NSString *fdTagName = tName;
	if (fdTagName != nil) {
		id previousValue = [allFieldDescriptorsByTagNames objectForKey: fdTagName];
		[allFieldDescriptorsByTagNames setValue: fieldDescriptor forKey: fdTagName];
		if (previousValue != nil) NSLog(@"tag <%@>:\t field[%@] overrides field[%@%]", fdTagName, [fieldDescriptor getFieldName], [previousValue getFieldName]);
	}
}

@end