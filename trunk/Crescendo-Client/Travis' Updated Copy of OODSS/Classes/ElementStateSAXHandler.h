//
//  ElementStateSAXHandler.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/14/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <stdio.h>
#import <objc/runtime.h>
#import <Foundation/Foundation.h>

#import "FieldDescriptor.h"
#import "ClassDescriptor.h"
#import "TranslationScope.h"
#import "ElementState.h"

#import "XMLTools.h"
#import "types.h"


@interface ElementStateSAXHandler : NSObject {
	TranslationScope *translationScope;
	ElementState *root;
	ElementState *currentElementState;
	FieldDescriptor *currentFieldDescriptor;
	NSMutableArray *fdStack;
	NSMutableString *currentTextValue;

	BOOL success;
}

+ (id) handlerWithTranslationScope: (TranslationScope *) scope;

- (id) initWithTranslationScope: (TranslationScope *) scope;
- (id) currentClassDescriptor;

- (void) parserDidStartDocument: (NSXMLParser *) parser;
- (void) parser: (NSXMLParser *) parser didStartElement: (NSString *) elementName namespaceURI: (NSString *) namespaceURI qualifiedName: (NSString *) qName attributes: (NSDictionary *) attributeDict;
- (void) parser: (NSXMLParser *) parser didEndElement: (NSString *) elementName namespaceURI: (NSString *) namespaceURI qualifiedName: (NSString *) qName;
- (void) parser: (NSXMLParser *) parser foundCharacters: (NSString *) string;
- (void) parser: (NSXMLParser *) parser parseErrorOccurred: (NSError *) parseError;
- (void) setRoot: (ElementState *) newRoot;
- (void) processPendingScalar: (int) type elementState: (ElementState *) elementState;
- (void) pushFieldDescriptor: (FieldDescriptor *) fieldDescriptor;
- (void) popAndPeekFieldDescriptor;

- (ElementState *) parse: (NSString *) pathToFile;
- (ElementState *) parseData: (NSData *) data;

@end