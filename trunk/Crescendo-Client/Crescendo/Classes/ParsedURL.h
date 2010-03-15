//
//  ParsedURL.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <Foundation/Foundation.h>

@class ElementState;
@class TranslationScope;

@interface ParsedURL : NSObject {
	//private static final String	NOT_IN_THE_FORMAT_OF_A_WEB_ADDRESS = " is not in the format of a web address";
	//private static final String	DEFAULT_USER_AGENT	= "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.7) Gecko/20070914 Firefox/2.0.0.7";

	NSURL *url;
	NSFileHandle *file;
	NSURL *hashUrl;
	NSURL *directory;

	ParsedURL *directoryPURL;

	NSString *string;
	NSString *shortString;
	NSString *lc;
	NSString *suffix;
	NSString *domain;
}

+ (id) parsedURLWithURL: (NSURL *) newURL;
+ (id) parsedURLWithFile: (NSFileHandle *) fileHandle;
+ (id) parsedURLWithAbsoluteAddress: (NSString *) webAddr;
+ (id) parsedURLWithAbsoluteAddress: (NSString *) webAddr withErrorDescription: (NSString *) errorDescription;
+ (id) parsedURLWithRelativePath: (NSURL *) url relativeURLPath: (NSString *) relativeURLPath withErrorDescription: (NSString *) errorDescription;
+ (NSURL *) getURL: (NSURL *) base withPath: (NSString *) path withErro: (NSString *) error;

- (id) iniWithURL: (NSURL *) newURL;
- (id) initWithFile: (NSFileHandle *) fileHandle;

- (BOOL) isUndetectedMalformedURL: (NSURL *) url;
- (BOOL) isNotFileOrExisits;
- (ParsedURL *) getRelative: (NSString *) relativeURLPath;
- (ParsedURL *) getRelative: (NSString *) relativeURLPath withErrorDescription: (NSString *) errorDescription;
- (ElementState *) translateFromXML: (TranslationScope *) TranslationScope;
- (NSString *) description;
- (NSString *) lc;
- (NSString *) suffix;
- (NSString *) suffix: (NSString *) lc;
- (ParsedURL *) directoryPURL;
- (NSURL *) directory;
- (NSString *) domain;
- (NSString *) filename;
- (NSURL *) url;
- (NSURL *) hashUrl;
- (BOOL) isNull;
- (NSString *) noAnchorNoQueryPageString;
- (NSString *) noAnchorPageString;
- (BOOL) hasSuffix: (NSString *) s;

@end