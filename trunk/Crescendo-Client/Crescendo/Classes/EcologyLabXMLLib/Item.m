#import "Item.h"

@implementation Item

@synthesize title;
@synthesize description;
@synthesize link;
@synthesize guid;
@synthesize author;
@synthesize categorySet;

+ (void) initialize {
	[Item class];
}


@end

