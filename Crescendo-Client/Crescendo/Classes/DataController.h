//
//  DataController.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface DataController : NSObject {
	NSMutableArray *list;
}

- (unsigned) countOfList;
- (id) objectInListAtIndex: (unsigned) theIndex;
- (void) addData: (NSString *) data;
- (void) removeDataAtIndex: (unsigned) theIndex;

@property (nonatomic, copy, readwrite) NSMutableArray *list;

@end
