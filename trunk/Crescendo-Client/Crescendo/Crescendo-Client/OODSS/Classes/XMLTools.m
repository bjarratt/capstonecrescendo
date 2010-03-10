//
//  XMLTools.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "XMLTools.h"


@implementation XMLTools

+ (NSString *) getClassSimpleName : (NSString *) classFullName {
	NSArray *splitString = [classFullName componentsSeparatedByString: @"."];
	return (NSString *)[splitString objectAtIndex: splitString.count - 1];
}

+ (Class) getClass: (NSString *) className {
	return (Class)objc_getClass([[XMLTools getClassSimpleName: className] cStringUsingEncoding: NSASCIIStringEncoding]);
}

+ (const char *) getSetterFunction: (const char *) fieldName {
	//TODO :  works but do this a better way!
	NSString *fName = [NSString stringWithCString: fieldName];
	NSString *capitalizedFieldName = [fName stringByReplacingCharactersInRange: NSMakeRange(0, 1)  withString:[[fName substringToIndex: 1] capitalizedString]];
	NSString *functionName = [NSString stringWithFormat: @"set%@WithReference:", capitalizedFieldName];
	return [functionName cStringUsingEncoding: NSASCIIStringEncoding];
}

+ (id <Type>) typeWithString: (NSString *) value {
	Class type = [XMLTools getClass: value];
	return class_createInstance(type, 0);
}

+ (NSString *) getTypeFromField: (Ivar) field {
	NSString *result = [NSString stringWithCString: ivar_getTypeEncoding(field)];
	return [result substringWithRange: NSMakeRange(2, [result length] - 3)];
}

+ (const char *) getCTypeFromField: (Ivar) field {
	NSString *result = [NSString stringWithCString: ivar_getTypeEncoding(field)];
	return [[result substringWithRange: NSMakeRange(2, [result length] - 3)] cStringUsingEncoding: NSASCIIStringEncoding];
}

+ (id) getInstance: (Class *) class {
	return class_createInstance(*class, 0);
}

@end