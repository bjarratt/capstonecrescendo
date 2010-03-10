//
//  ChatViewController.h
//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface ChatViewController : UIViewController <UITableViewDataSource> {
  IBOutlet UITableView* serverList;
}

- (IBAction)joinChatRoom:(id)sender;

// View is active, start everything up
- (void)activate;

@end

