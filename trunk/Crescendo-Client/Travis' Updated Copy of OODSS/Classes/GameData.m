#import "GameData.h"


static 	TranslationScope *translationScope;


@implementation GameData

@synthesize timestamp;
@synthesize cycRem;
@synthesize paused;
@synthesize loaded;
@synthesize over;
@synthesize running;
@synthesize won;
@synthesize threats;
@synthesize score;

+ (void) initialize {
	[GameData class];
}

/*
 * Create Translation Scope from XML file
 */
+ (TranslationScope *) getTranslationScope {
	if(translationScope == nil){
		NSString *path = [[[NSBundle mainBundle] resourcePath] 
						  stringByAppendingPathComponent: @"gamedata_translationScope.xml"];
		translationScope = [[TranslationScope alloc] initWithXMLFilePath: path];	
	}
	return translationScope;
}

- (void) setTimestampWithReference: (long *) p_timestamp {
	timestamp = *p_timestamp;
}
- (void) setCycRemWithReference: (int *) p_cycRem {
	cycRem = *p_cycRem;
}
- (void) setPausedWithReference: (bool *) p_paused {
	paused = *p_paused;
}
- (void) setLoadedWithReference: (bool *) p_loaded {
	loaded = *p_loaded;
}
- (void) setOverWithReference: (bool *) p_over {
	over = *p_over;
}
- (void) setRunningWithReference: (bool *) p_running {
	running = *p_running;
}
- (void) setWonWithReference: (bool *) p_won {
	won = *p_won;
}
- (void) setScoreWithReference: (double *) p_score {
	score = *p_score;
}

@end

