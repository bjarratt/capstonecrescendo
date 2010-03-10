//
//  ChatTranslations.m
//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "ChatTranslations.h"

@implementation ChatTranslations

static TranslationScope* theScope;

/*
 * Class level accessor for ChatTranslations
 */
+(TranslationScope*) get
{
  if(theScope == nil)
  { 
    [OkResponse class];
    /*
     * Initiate translation scope with xml file resource.
     */
    NSString *path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent: @"ChatTranslations.xml"];
    theScope = [[[TranslationScope alloc] initWithXMLFilePath: path] retain];
  }
  
  return theScope;
}

@end
