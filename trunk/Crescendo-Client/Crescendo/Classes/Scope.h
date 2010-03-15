//
//  NSScope.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Scope : NSObject {
	NSDictionary *m_parent;
	NSMutableDictionary *mutableDictionary;
}

@property (readwrite, retain) NSDictionary *m_parent;

#pragma mark Scope - class initializers
+ (id) scope;
+ (id) scopeWithParent: (NSDictionary *) parent;
+ (id) scopeWithCapacity: (NSUInteger) capacity;
+ (id) scopeWithParentAndCapacity: (NSDictionary *) parent withCapacity: (NSUInteger) capacity;

#pragma mark Scope - instance initializers
- (id) init;
- (id) initWithParent: (NSDictionary *) parent;
- (id) initWithCapacity: (NSUInteger) capacity;
- (id) initWithParentAndCapacity: (NSDictionary *) parent withCapacity: (NSUInteger) capacity;

#pragma mark Scope - instance functions
- (id) objectForKey: (id) aKey;
- (void) setObject: (id) anObject forKey: (id) aKey;
- (void) setParent: (NSDictionary *) newParent;
- (NSDictionary *) operativeParent;
- (NSString *) toString;
- (NSString *) sizeMsg;
- (NSString *) dump;
- (void) dump: (NSString *) result withPrefix: (NSString *) prefix;

@end