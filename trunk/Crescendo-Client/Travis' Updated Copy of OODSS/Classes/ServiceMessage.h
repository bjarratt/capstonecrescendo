//
//  ServiceMessage.h
//  ecologylabXML
//
//  Created by William Hamilton on 1/22/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ecologylabxml.h"

@interface ServiceMessage : ElementState
{
  long timeStamp;
  long uid;
}

@property (nonatomic,readwrite) long timeStamp;
@property (nonatomic,readwrite) long uid;

- (void) setTimeStampWithReference: (long *) p_timeStamp;
- (void) setUidWithReference: (long *) p_uid;


- (id) initTestObject;

@end
