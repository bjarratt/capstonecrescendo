#import <Foundation/Foundation.h>
#import "ElementState.h"
#import "ParsedURL.h"

@interface Item : ElementState
{
	NSString *title;
	NSString *description;
	ParsedURL *link;
	ParsedURL *guid;
	NSString *author;
	NSMutableArray *categorySet;
}

@property (nonatomic,readwrite, retain) NSString *title;
@property (nonatomic,readwrite, retain) NSString *description;
@property (nonatomic,readwrite, retain) ParsedURL *link;
@property (nonatomic,readwrite, retain) ParsedURL *guid;
@property (nonatomic,readwrite, retain) NSString *author;
@property (nonatomic,readwrite, retain) NSMutableArray *categorySet;

@end

