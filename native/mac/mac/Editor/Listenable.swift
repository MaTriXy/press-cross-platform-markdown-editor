//
// Created by Saket Narayan on 5/13/20.
// Copyright (c) 2020 Saket Narayan. All rights reserved.
//

import SwiftUI

class Listenable<T>: ObservableObject {
  private let listener: (T) -> Void
  @Published var value: T {
    didSet {
      listener(value)
    }
  }

  init(initial: T, _ listener: @escaping (T) -> Void) {
    self.value = initial
    self.listener = listener
  }
}
