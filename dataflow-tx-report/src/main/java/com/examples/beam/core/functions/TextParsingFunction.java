package com.examples.beam.core.functions;

import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;

@Slf4j
public abstract class TextParsingFunction<T> extends DoFn<String, T> {
}
