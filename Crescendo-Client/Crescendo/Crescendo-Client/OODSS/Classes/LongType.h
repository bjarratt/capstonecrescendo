//
//  LongType.h
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


#import "ScalarType.h"

@class FieldDescriptor;

@interface LongType : ScalarType {
}

+ (id) longTypeWithString: (NSString *) value;

@end
