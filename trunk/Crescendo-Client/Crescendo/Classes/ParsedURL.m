//
//  ParsedURL.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/8/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "ParsedURL.h"


@implementation ParsedURL

+ (id) parsedURLWithURL : (NSURL *) newURL {
	return [[[ParsedURL alloc] iniWithURL: newURL] autorelease];
}

+ (id) parsedURLWithFile: (NSFileHandle *) fileHandle {
	return nil;
}

+ (id) parsedURLWithAbsoluteAddress: (NSString *) webAddr {
	return nil;
}

+ (id) parsedURLWithAbsoluteAddress: (NSString *) webAddr withErrorDescription: (NSString *) errorDescription {
	return nil;
}

+ (id) parsedURLWithRelativePath: (NSURL *) url relativeURLPath: (NSString *) relativeURLPath withErrorDescription: (NSString *) errorDescription {
	return nil;
}

+ (NSURL *) getURL: (NSURL *) base withPath: (NSString *) path withErro: (NSString *) error {
	return nil;
}

- (id) iniWithURL: (NSURL *) newURL {
	if ( (self ==[super init]) ) {
		url = newURL;
	}
	return self;
}

- (id) initWithFile: (NSFileHandle *) fileHandle {
	return nil;
}

- (NSString *) description {
	return [url description];
}

- (BOOL) isUndetectedMalformedURL: (NSURL *) url {
	return NO;
}

- (BOOL) isNotFileOrExisits {
	return NO;
}

- (ParsedURL *) getRelative: (NSString *) relativeURLPath {
	return nil;
}

- (ParsedURL *) getRelative: (NSString *) relativeURLPath withErrorDescription: (NSString *) errorDescription {
	return nil;
}

- (ElementState *) translateFromXML: (TranslationScope *) TranslationScope {
	return nil;
}

- (NSString *) lc {
	return nil;
}

- (NSString *) suffix {
	return nil;
}

- (NSString *) suffix: (NSString *) lc {
	return nil;
}

- (ParsedURL *) directoryPURL {
	return nil;
}

- (NSURL *) directory {
	return nil;
}

- (NSString *) domain {
	return nil;
}

- (NSString *) filename {
	return nil;
}

- (NSURL *) url {
	return nil;
}

- (NSURL *) hashUrl {
	return nil;
}

- (BOOL) isNull {
	return NO;
}

- (NSString *) noAnchorNoQueryPageString {
	return nil;
}

- (NSString *) noAnchorPageString {
	return nil;
}

- (BOOL) hasSuffix: (NSString *) s {
	return NO;
}

@end