#import "Entity.h"

@implementation Entity

@synthesize m_id;
@synthesize online;
@synthesize m_in;
@synthesize safe;
@synthesize ord;

+ (void) initialize {
	[Entity class];
}

- (void) setOnlineWithReference: (bool *) p_online {
	online = *p_online;
}
- (void) setInWithReference: (bool *) p_in {
	m_in = *p_in;
}
- (void) setSafeWithReference: (bool *) p_safe {
	safe = *p_safe;
}
- (void) setOrdWithReference: (int *) p_ord {
	ord = *p_ord;
}

@end

