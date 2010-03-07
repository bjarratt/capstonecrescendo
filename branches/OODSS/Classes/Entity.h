#import <Foundation/Foundation.h>
#import "ElementState.h"

@interface Entity : ElementState
{
	NSString *m_id;
	bool online;
	bool m_in;
	bool safe;
	int ord;
}

@property (nonatomic,readwrite, retain) NSString *m_id;
@property (nonatomic,readwrite) bool online;
@property (nonatomic,readwrite) bool m_in;
@property (nonatomic,readwrite) bool safe;
@property (nonatomic,readwrite) int ord;

- (void) setOnlineWithReference: (bool *) p_online;

- (void) setInWithReference: (bool *) p_in;

- (void) setSafeWithReference: (bool *) p_safe;

- (void) setOrdWithReference: (int *) p_ord;

@end

