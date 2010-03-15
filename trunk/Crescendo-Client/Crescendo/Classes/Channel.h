#import <Foundation/Foundation.h>
#import "ElementState.h"
#import "ParsedURL.h"

@interface Channel : ElementState
{
	NSString *title;
	NSString *description;
	ParsedURL *link;
	NSMutableArray *items;
}

@property (nonatomic,readwrite, retain) NSString *title;
@property (nonatomic,readwrite, retain) NSString *description;
@property (nonatomic,readwrite, retain) ParsedURL *link;
@property (nonatomic,readwrite, retain) NSMutableArray *items;

@end

