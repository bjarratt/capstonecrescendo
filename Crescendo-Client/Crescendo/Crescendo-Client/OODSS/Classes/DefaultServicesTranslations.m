//
//  DefaultServicesTranslations.m
//  ecologylabXML
//
//  Created by William Hamilton on 1/25/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "DefaultServicesTranslations.h"


@implementation DefaultServicesTranslations

static TranslationScope* theScope;

+(TranslationScope*) get
{
  if( true)
  {
    /* make sure classes are registered in runtime */
    [ServiceMessage class];
    [RequestMessage class];
    [ResponseMessage class];
    [ErrorResponse class];
    [DisconnectRequest class];
    [HistoryEchoRequest class];
    [HistoryEchoResponse class];
    [InitConnectionRequest class];
    [InitConnectionResponse class];
     
    
    NSString *path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent: @"DefaultServicesTranslations.xml"];
    theScope = [[[TranslationScope alloc] initWithXMLFilePath: path] retain];
  }
  
  return theScope;
}


@end
