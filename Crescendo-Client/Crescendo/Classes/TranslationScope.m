


#import "TranslationScope.h"
#import "ClassDescriptor.h"
#import "FieldDescriptor.h"
#import "bsConstants.h"


@interface TranslationScope (private)

- (void) parserDidStartDocument: (NSXMLParser *) parser;
- (void) parser: (NSXMLParser *) parser didStartElement: (NSString *) elementName namespaceURI: (NSString *) namespaceURI qualifiedName: (NSString *) qName attributes: (NSDictionary *) attributeDict;
- (void) parser: (NSXMLParser *) parser didEndElement: (NSString *) elementName namespaceURI: (NSString *) namespaceURI qualifiedName: (NSString *) qName;
- (void) parser: (NSXMLParser *) parser foundCharacters: (NSString *) string;
- (void) parser: (NSXMLParser *) parser parseErrorOccurred: (NSError *) parseError;

- (void) processPendingFieldDescriptor;
- (void) pushFieldDescriptor: (FieldDescriptor *) fd;
- (FieldDescriptor *) popAndPeekFieldDescriptor;

@end

@implementation TranslationScope

- (TranslationScope *) initWithXMLFilePath: (NSString *) pathToFile {
	[self init];

	NSURL *xmlURL = [NSURL fileURLWithPath: pathToFile];
	NSXMLParser *addressParser = [[NSXMLParser alloc] initWithContentsOfURL: xmlURL];

	[addressParser setDelegate: self];
	[addressParser setShouldResolveExternalEntities: YES];

	success = [addressParser parse];

	[addressParser release];
	return self;
}

- (ClassDescriptor *) getClassDescriptorByTag: (NSString *) tagName {
	return [entriesByTag objectForKey: tagName];
}

- (id) init {
	if ( (self = [super init]) ) {
		success = NO;
		classDescriptor = [ClassDescriptor classDescriptor];
		fdStack = [NSMutableArray array];
		entriesByTag = [[NSMutableDictionary dictionary] retain];
	}

	return self;
}

- (void) dealloc {
	[name release];
	[entriesByTag release];
	[super dealloc];
}

@end

@implementation TranslationScope (private)

- (void) parserDidStartDocument: (NSXMLParser *) parser {
}

- (void) parser: (NSXMLParser *) parser didStartElement: (NSString *) elementName namespaceURI: (NSString *) namespaceURI qualifiedName: (NSString *) qName attributes: (NSDictionary *) attributeDict {
	if ([elementName isEqualToString: TRANSLATION_SCOPE]) {
		StringType *stTagName = [StringType stringTypeWithString:[attributeDict valueForKey: TRANSLATION_SCOPE_NAME]];
		[stTagName setField: self fieldName: "name"];
	}
	else if ([elementName isEqualToString: CLASS_DESCRIPTOR]) {
		StringType *stTagName = [StringType stringTypeWithString:[attributeDict valueForKey: CLASS_DESCRIPTOR_TAG_NAME]];
		ClassType  *ctDescribedClass =  [ClassType classTypeWithString:[XMLTools getClassSimpleName:[attributeDict valueForKey: CLASS_DESCRIPTOR_DESCRIBED_CLASS]]];

		[stTagName setField: classDescriptor fieldName: "tagName"];
		[ctDescribedClass setField: classDescriptor fieldName: "describedClass"];
	}
	else if ([elementName isEqualToString: FIELD_DESCRIPTOR]) {
		fieldDescriptor = [FieldDescriptor fieldDescriptor];

		StringType *stTagName = [StringType stringTypeWithString:[attributeDict valueForKey: FIELD_DESCRIPTOR_TAG_NAME]];
		FieldType *ftField =   [FieldType fieldTypeWithStringAndClass:[attributeDict valueForKey: FIELD_DESCRIPTOR_FIELD] containerClass: classDescriptor.describedClass];
		IntType *itType = [IntType intTypeWithString:[attributeDict valueForKey: FIELD_DESCRIPTOR_TYPE]];
		BooleanType *btNeedsEscaping = [BooleanType booleanTypeWithString:[attributeDict valueForKey: FIELD_DESCRIPTOR_NEEDS_ESCAPING]];
		ReferenceTypeType *tType = [ReferenceTypeType referenceTypeTypeWithString:[attributeDict valueForKey: FIELD_DESCRIPTOR_SCALAR_TYPE]];
		ClassType  *ctDescribedClass =  [ClassType classTypeWithString:[XMLTools getClassSimpleName:[attributeDict valueForKey: FIELD_DESCRIPTOR_ELEMENT_CLASS]]];

		StringType *stCollectionOrMapTagName = [StringType stringTypeWithString:[attributeDict valueForKey: FIELD_DESCRIPTOR_COLLECTION_TAG]];
		BooleanType *btWrapped = [BooleanType booleanTypeWithString:[attributeDict valueForKey: FIELD_DESCRIPTOR_WRAPPED]];

		[stTagName setField: fieldDescriptor fieldName: "tagName"];
		[ftField setField: fieldDescriptor fieldName: "field"];
		[itType setField: fieldDescriptor fieldName: "type"];
		[tType setField: fieldDescriptor fieldName: "scalarType"];
		[btNeedsEscaping setField: fieldDescriptor fieldName: "needsEscaping"];
		[stCollectionOrMapTagName setField: fieldDescriptor fieldName: "collectionOrMapTagName"];
		[btWrapped setField: fieldDescriptor fieldName: "isWrapped"];
		[ctDescribedClass setField: fieldDescriptor fieldName: "elementClass"];
		[fieldDescriptor setDeclaringClassDescriptor: classDescriptor];

		[classDescriptor addFieldDescriptor: fieldDescriptor];
	}
	else if ([elementName isEqualToString: TAG_CLASSES]) {
		addTagClasses = YES;
	}
}

- (void) parser: (NSXMLParser *) parser didEndElement: (NSString *) elementName namespaceURI: (NSString *) namespaceURI qualifiedName: (NSString *) qName {
	if ([elementName isEqualToString: CLASS_DESCRIPTOR]) {
		[entriesByTag setObject: classDescriptor forKey: classDescriptor.tagName];
		[[ClassDescriptor globalClassDescriptorsMap] setObject: classDescriptor forKey:[classDescriptor describedClassName]];
		classDescriptor = [ClassDescriptor classDescriptor];
	}
	else if ([elementName isEqualToString: TAG_CLASSES]) {
		addTagClasses = NO;
	}
	else if ([elementName isEqualToString: TAG_CLASSES_OUTER]) {
		[self pushFieldDescriptor: fieldDescriptor];
	}
	else if ([elementName isEqualToString: TRANSLATION_SCOPE]) {
		[self processPendingFieldDescriptor];
	}
}

- (void) parser: (NSXMLParser *) parser foundCharacters: (NSString *) string {
	if (addTagClasses) {
		ClassType  *ctDescribedClass =  [ClassType classTypeWithString:[XMLTools getClassSimpleName: string]];
		[fieldDescriptor addTagClass:[XMLTools getClassSimpleName: string] tagClass: (Class *)[ctDescribedClass getInstance]];
	}
}

- (void) parser: (NSXMLParser *) parser parseErrorOccurred: (NSError *) parseError {
	NSLog(@"parse error occurred : %@", [parseError localizedDescription]);
}

- (void) pushFieldDescriptor: (FieldDescriptor *) fd {
	[fdStack addObject: fd];
}

- (FieldDescriptor *) popAndPeekFieldDescriptor {
	id result = nil;
	int last = [fdStack count] - 1;
	if (last >= 0) {
		result = [fdStack objectAtIndex: last];
		[fdStack removeLastObject];
	}
	return result;
}

- (void) processPendingFieldDescriptor {
	FieldDescriptor *fd = nil;
	while ( (fd = [self popAndPeekFieldDescriptor]) != nil ) {
		for (Class class in[fd.tagClasses allValues]) {
			ClassDescriptor *polymorphicClassDescriptor = [ClassDescriptor classDescriptor: class];
			[[fd declaringClassDescriptor] addFieldDescriptorMapping:[polymorphicClassDescriptor tagName] fieldDescriptor: fd];
			[fd addTagClassDescriptor:[polymorphicClassDescriptor tagName] tagClass: polymorphicClassDescriptor];
		}
	}
}

@end