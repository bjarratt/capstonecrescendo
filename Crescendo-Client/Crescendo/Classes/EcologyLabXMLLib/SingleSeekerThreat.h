#import <Foundation/Foundation.h>
#import "Threat.h"

@interface SingleSeekerThreat : Threat
{
	int targetOrd;
}

@property (nonatomic,readwrite) int targetOrd;

- (void) setTargetOrdWithReference: (int *) p_targetOrd;

@end

