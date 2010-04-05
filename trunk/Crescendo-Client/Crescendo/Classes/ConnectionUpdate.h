//
//  ConnectionUpdate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UpdateMessage.h"

@interface ConnectionUpdate : UpdateMessage {
	NSString *playerNumber;
}

@property (nonatomic,readwrite, retain) NSString *playerNumber;

@end
