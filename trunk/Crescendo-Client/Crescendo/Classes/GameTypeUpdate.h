//
//  GameTypeUpdate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UpdateMessage.h"

@interface GameTypeUpdate : UpdateMessage 
{
	NSString *currentType;
}

@property (nonatomic,readwrite, retain) NSString *currentType;

@end
