#import <Foundation/Foundation.h>
#import "ElementState.h"
#import "Channel.h"
#import "TranslationScope.h"

@interface RssState : ElementState
{
	float version;
	Channel *channel;
}

@property (nonatomic,readwrite) float version;
@property (nonatomic,readwrite, retain) Channel *channel;

+ (TranslationScope *) getTranslationScope;

- (void) setVersionWithReference: (float *) p_version;

@end

