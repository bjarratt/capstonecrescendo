#import <Foundation/Foundation.h>
#import "ServiceMessage.h"

@interface UpdateMessage : ServiceMessage
{
}

-(void) processUpdate:(Scope *) scope;

@end

