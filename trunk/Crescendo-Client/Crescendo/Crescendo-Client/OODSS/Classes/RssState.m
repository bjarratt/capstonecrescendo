#import "RssState.h"

static 	TranslationScope *translationScope;

@implementation RssState

@synthesize version;
@synthesize channel;


+ (TranslationScope *) getTranslationScope {
	if(translationScope == nil){
		NSString *path = [[[NSBundle mainBundle] resourcePath] 
						  stringByAppendingPathComponent: @"RssTranslationScope.xml"];
		translationScope = [[TranslationScope alloc] initWithXMLFilePath: path];	
	}
	return translationScope;
}


+ (void) initialize {
	[RssState class];
}

- (void) setVersionWithReference: (float *) p_version {
	version = *p_version;
}

@end

