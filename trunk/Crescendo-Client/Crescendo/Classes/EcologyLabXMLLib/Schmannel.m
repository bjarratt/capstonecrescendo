//
//  Channel.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/7/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "Schmannel.h"

static 	TranslationScope *translationScope;

@implementation Schmannel

@synthesize schmItems;
@synthesize polyItem;

/*
 * Create and return TranslationScope by reading the Schmannel.xml file
 */
+ (TranslationScope *) getTranslationScope {
	if(translationScope == nil){
		NSString *path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent: @"Schmannel.xml"];
		translationScope = [[TranslationScope alloc] initWithXMLFilePath: path];	
	}
	return translationScope;
}

@end

