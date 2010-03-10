//
//  NetworkConstants.m
//
//  Created by William Hamilton on 1/8/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "NetworkConstants.h"

int const DEFAULT_MAX_MESSAGE_LENGTH_CHARS = 128 * 1024;
int const DEFAULT_IDLE_TIMEOUT = 10000;
int const MAX_HTTP_HEADER_LENGTH = 4 * 1024;

NSString * const CONTENT_LENGTH_STRING = @"content-length";
NSString * const UNIQUE_IDENTIFIER_STRING = @"uid";
NSString * const HTTP_HEADER_LINE_DELIMETER = @"\r\n";
NSString * const HTTP_HEADER_TERMINATOR = @"\r\n\r\n";
NSString * const HTTP_CONTENT_CODING = @"content-encoding";
