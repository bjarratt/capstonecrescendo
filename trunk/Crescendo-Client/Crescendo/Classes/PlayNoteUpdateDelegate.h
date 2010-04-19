//
//  PlayNoteUpdateDelegate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "PlayNoteUpdate.h"

extern NSString * const PLAYNOTE_UPDATE_DELEGATE;

@protocol PlayNoteUpdateDelegate
- (void) recievedPlayNoteUpdate:(PlayNoteUpdate*)update;
@end
