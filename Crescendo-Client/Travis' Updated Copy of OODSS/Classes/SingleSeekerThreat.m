#import "SingleSeekerThreat.h"

@implementation SingleSeekerThreat

@synthesize targetOrd;

+ (void) initialize {
	[SingleSeekerThreat class];
}

- (void) setTargetOrdWithReference: (int *) p_targetOrd {
	targetOrd = *p_targetOrd;
}

@end

