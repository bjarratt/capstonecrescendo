//
//  ConnectionRequest.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RequestMessage.h"

@interface ConnectionRequest : RequestMessage {
	NSString *message;
}

@property (nonatomic,readwrite, retain) NSString *message;

@end
