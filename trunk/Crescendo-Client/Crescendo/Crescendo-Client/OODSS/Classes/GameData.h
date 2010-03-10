#import <Foundation/Foundation.h>
#import "ElementState.h"
#import "TranslationScope.h"

@interface GameData : ElementState
{
	long timestamp;
	int cycRem;
	bool paused;
	bool loaded;
	bool over;
	bool running;
	bool won;
	NSMutableArray *threats;
	double score;
}

@property (nonatomic,readwrite) long timestamp;
@property (nonatomic,readwrite) int cycRem;
@property (nonatomic,readwrite) bool paused;
@property (nonatomic,readwrite) bool loaded;
@property (nonatomic,readwrite) bool over;
@property (nonatomic,readwrite) bool running;
@property (nonatomic,readwrite) bool won;
@property (nonatomic,readwrite, retain) NSMutableArray *threats;
@property (nonatomic,readwrite) double score;

+ (TranslationScope *) getTranslationScope;

- (void) setTimestampWithReference: (long *) p_timestamp;

- (void) setCycRemWithReference: (int *) p_cycRem;

- (void) setPausedWithReference: (bool *) p_paused;

- (void) setLoadedWithReference: (bool *) p_loaded;

- (void) setOverWithReference: (bool *) p_over;

- (void) setRunningWithReference: (bool *) p_running;

- (void) setWonWithReference: (bool *) p_won;

- (void) setScoreWithReference: (double *) p_score;

@end

