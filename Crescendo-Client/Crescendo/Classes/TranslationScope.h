//
//  TranslationScope.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//


/*!
 @header TranslationScope The Repast Manager provides a functional interface to the repast driver. Use the functions declared here to generate, distribute, and consume meals.
 @copyright Interface Ecology Lab
 @updated 01-01-10
*/

#import <stdio.h>
#import <objc/runtime.h>
#import <Foundation/Foundation.h>

#import "XMLTools.h"
#import "types.h"

@class FieldDescriptor;
@class ClassDescriptor;

/*!
    @class		 TranslationScope
    @abstract    Class to manages the translation scope 
    @discussion  test discussion
*/

@interface TranslationScope : NSObject
{
	@private NSString *name;
	@private NSMutableDictionary *entriesByTag;
	@private ClassDescriptor *classDescriptor;
	@private FieldDescriptor *fieldDescriptor;
	@private NSMutableArray *fdStack;
	@private BOOL success;
	@private BOOL addTagClasses;
}

/*!
    @method     initWithXMLFilePath
    @abstract   brief description
    @discussion test discussion
	@param pathToFile NSString representing the path of the file to use for translation
*/
- (TranslationScope *) initWithXMLFilePath: (NSString *) pathToFile; 
- (ClassDescriptor *) getClassDescriptorByTag: (NSString *) tagName;


@end