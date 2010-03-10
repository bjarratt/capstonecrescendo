//
//  NSDictionaryList.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface DictionaryList : NSObject <NSFastEnumeration> {
	NSMutableArray *mutableArray;
	NSMutableDictionary *mutableDictionary;
}

@property (readwrite, retain) NSMutableArray *mutableArray;

#pragma mark NSScope - class initializers
+ (id) dictionaryList;
+ (id) dictionaryListFromCapacity: (NSUInteger) capacity;
+ (id) dictionaryListFromDictionary: (NSDictionary *) dictionary;

#pragma mark NSScope - instance initializers
- (id) init;
- (id) initFromCapacity: (NSUInteger) capacity;
- (id) initFromDictionary: (NSDictionary *) dictionary;

#pragma mark NSScope - instance functions
- (id) objectAtIndex: (NSUInteger) aKey;
- (void) setObject: (id) anObject forKey: (id) aKey;
- (NSArray *) allValues;
- (void) recycle;

@end