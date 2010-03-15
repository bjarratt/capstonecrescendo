

#import <Foundation/Foundation.h>

@class XMLClient;

@protocol XMLClientDelegate

- (void) connectionTerminated:(XMLClient*)connection;
- (void) connectionAttemptFailed:(XMLClient*) connection;
- (void) connectionSuccessful:(XMLClient*) connection withSessionId:(NSString*) sessionId;

@end
