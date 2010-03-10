//
//  DefaultServicesTranslations.h
//  ecologylabXML
//
//  Created by William Hamilton on 1/25/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "TranslationScope.h"
#import "InitConnectionResponse.h"
#import "InitConnectionRequest.h"
#import "DisconnectRequest.h"
#import "ErrorResponse.h"
#import "HistoryEchoRequest.h"
#import "HistoryEchoResponse.h"
#import "OkResponse.h"
#import "UpdateMessage.h"

@interface DefaultServicesTranslations : NSObject {

}

+(TranslationScope*) get;

@end