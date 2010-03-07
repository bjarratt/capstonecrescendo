//
//  Channel.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/7/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "ElementState.h"
#import "Item.h"
#import "TranslationScope.h"

@interface Schmannel : ElementState
{
	NSMutableArray *schmItems;
	Item *polyItem;
}

@property (nonatomic,readwrite, retain) NSMutableArray *schmItems;
@property (nonatomic,readwrite, retain) Item *polyItem;

+ (TranslationScope*) getTranslationScope;

@end

