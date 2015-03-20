var arr = 'SystemGoal: Program $ \nProgram: ProgramHeading ";" Block "." \nProgramHeading: "program" ProgramIdentifier \nBlock: VariableDeclarationPart ProcedureAndFunctionDeclarationPart StatementPart \nVariableDeclarationPart: "var" VariableDeclaration ";" VariableDeclarationTail \nVariableDeclarationPart: @@ \nVariableDeclarationTail: VariableDeclaration ";" VariableDeclarationTail \nVariableDeclarationTail: @@ \nVariableDeclaration: Identifierlist ":" Type \nType: "Integer" \nType: "Float" \nProcedureAndFunctionDeclarationPart: ProcedureDeclaration ProcedureAndFunctionDeclarationPart \nProcedureAndFunctionDeclarationPart: FunctionDeclaration ProcedureAndFunctionDeclarationPart \nProcedureAndFunctionDeclarationPart: @@ \nProcedureDeclaration: ProcedureHeading ";" Block ";" \nFunctionDeclaration: FunctionHeading ";" Block ";" \nProcedureHeading: "procedure" procedureIdentifier OptionalFormalParameterList \nFunctionHeading: "function" functionIdentifier OptionalFormalParameterList Type \nOptionalFormalParameterList: "(" FormalParameterSection FormalParameterSectionTail ")" \nOptionalFormalParameterList: @@ \nFormalParameterSectionTail: ";" FormalParameterSection FormalParameterSectionTail \nFormalParameterSectionTail: @@ \nFormalParameterSection: ValueParameterSection \nFormalParameterSection: VariableParameterSection \nValueParameterSection: IdentifierList ":" Type \nVariableParameterSection: "var" IdentifierList ":" Type \nStatementPart: CompoundStatement \nCompoundStatement: "begin" StatementSequence "end" \nStatementSequence: Statement StatementTail \nStatementTail: ";" Statement StatementTail \nStatementTail: @@ \nStatement: EmptyStatement \nStatement: CompoundStatement \nStatement: ReadStatement \nStatement: WriteStatement \nStatement: AssignmentStatement \nStatement: IfStatement \nStatement: WhileStatement \nStatement: RepeatStatement \nStatement: ForStatement \nStatement: ProcedureStatement \nEmptyStatement : \nReadStatement: "read" "(" ReadParameter ReadParameterTail ")" \nReadParameterTail: "," ReadParameter ReadParameterTail \nReadParameterTail: @@ \nReadParameter: VariableIdentifier \nWriteStatement: "write" "(" WriteParameter WriteParameterTail ")" \nWriteParameterTail: "," WriteParameter WriteParameterTail \nWriteParameterTail: @@ \nWriteParameter: OrdinalExpression \nAssignmentStatement: VariableIdentifier ":=" Expression \nAssignmentStatement: FunctionIdentifier ":=" Expression \nIfStatement: "if" BooleanExpression "then" Statement OptionalElsePart \nOptionalElsePart: "else" Statement \nOptionalElsePart: @@ \nRepeatStatement: "repeat" StatementSequence "until" BooleanExpression \nWhileStatement: "while" BooleanExpression "do" Statement \nForStatement: "for" ControlVariable ":=" InitialValue StepValue FinalValue "do" Statement \nControlVariable: VariableIdentifier \nInitialValue: OrdinalExpression \nStepValue: "to" \nStepValue: "downto" \nFinalValue: OrdinalExpression \nProcedureStatement: ProcedureIdentifier OptionalActualParameterList \nOptionalActualParameterList: "(" ActualParameter ActualParameterTail ")" \nOptionalActualParameterList: @@ \nActualParameterTail: "," ActualParameter ActualParameterTail \nActualParameterTail: @@ \nActualParameter: OrdinalExpression \nExpression: SimpleExpression OptionalRelationalPart \nOptionalRelationalPart: RelationalOperator SimpleExpression \nOptionalRelationalPart: @@ \nRelationalOperator: "=" \nRelationalOperator: "<" \nRelationalOperator: ">" \nRelationalOperator: "<=" \nRelationalOperator: ">=" \nRelationalOperator: "<>" \nSimpleExpression: OptionalSign Term TermTail \nTermTail: AddingOperator Term TermTail \nTermTail: @@ \nOptionalSign: "+" \nOptionalSign: "-" \nOptionalSign: @@ \nAddingOperator: "+" \nAddingOperator: "-" \nAddingOperator: "or" \nTerm: Factor FactorTail \nFactorTail: MultiplyingOperator Factor FactorTail \nFactorTail: @@ \nMultiplyingOperator: "*" \nMultiplyingOperator: "div" \nMultiplyingOperator: "mod" \nMultiplyingOperator: "and" \nFactor: UnsignedInteger \nFactor: VariableIdentifier \nFactor: "not" Factor \nFactor: "(" Expression ")" \nFactor: FunctionIdentifier OptionalActualParameterList \nProgramIdentifier: Identifier \nVariableIdentifier: Identifier \nProcedureIdentifier: Identifier \nFunctionIdentifier: Identifier \nBooleanExpression: Expression \nOrdinalExpression: Expression \nIdentifierList: Identifier IdentifierTail \nIdentifierTail: "," Identifier IdentifierTail \nIdentifierTail: @@'.split('\n');

if (!Object.assign) {
  Object.defineProperty(Object, 'assign', {
    enumerable: false,
    configurable: true,
    writable: true,
    value: function(target, firstSource) {
      'use strict';
      if (target === undefined || target === null) {
        throw new TypeError('Cannot convert first argument to object');
      }

      var to = Object(target);
      for (var i = 1; i < arguments.length; i++) {
        var nextSource = arguments[i];
        if (nextSource === undefined || nextSource === null) {
          continue;
        }

        var keysArray = Object.keys(Object(nextSource));
        for (var nextIndex = 0, len = keysArray.length; nextIndex < len; nextIndex++) {
          var nextKey = keysArray[nextIndex];
          var desc = Object.getOwnPropertyDescriptor(nextSource, nextKey);
          if (desc !== undefined && desc.enumerable) {
            to[nextKey] = nextSource[nextKey];
          }
        }
      }
      return to;
    }
  });
}

function union_arrays (x, y) {
  var obj = {};
  for (var i = x.length-1; i >= 0; -- i)
     obj[x[i]] = x[i];
  for (var i = y.length-1; i >= 0; -- i)
     obj[y[i]] = y[i];
  var res = []
  for (var k in obj) {
    if (obj.hasOwnProperty(k))  // <-- optional
      res.push(obj[k]);
  }
  return res;
}

var makeTableRecurse = function (term, dict, grammar) {
    var start = term.rule.split(':')[0];
    var end = term.rule.split(':')[1];
  	if (end.trim() === 'lambda' || !dict[end.split(' ')[1].trim()]) {
          dict[start][end.split(' ')[1].trim()] = term.index;
          return { index: term.index, terminals: [end.split(' ')[1].trim()] };
  	}
    
    var expansionTerms = end.split(' ');
    var terms = [];
    var isEpsilon = true;
    for (var i = 1; i < expansionTerms.length && isEpsilon; i++) {
        var matchingRules = grammar.filter(function (a) { return a.rule.split(':')[0] === expansionTerms[i]; });
        var matches = matchingRules.map(function (a) {
             return makeTableRecurse(a, dict, grammar);
        });
        
        terms = union_arrays(terms, matches.reduce(function (a,b) {
            return a.concat(b.terminals);
        }, []));
        isEpsilon = matches.filter(function (a) {
            return a.terminals.indexOf('lambda') !== -1;
        }).length > 0;
        
        matches.forEach(function (a) {
            a.terminals.forEach(function (b) {
                dict[start][b] = dict[start][b] ? union_arrays(dict[start][b], [a.index]) : [a.index];
            });
        });

    }
    
    if (!isEpsilon) {
        terms = terms.filter(function (a) { return a !== 'lambda' });
    }
    
    return { index: term.index, terminals: terms };
};

var makeTable = function (arr) {
	var grammar = arr.map(function (a,b) { return { rule: a, index: b } });
	var lookup = grammar.reduce(function (a,b) { a[b.rule.split(':')[0]] = {}; return a; }, {});

    grammar.forEach(function (a) {
        makeTableRecurse(a, lookup, grammar);
    });
    
    return lookup;
};

var table = makeTable(arr);
var tokens = Object.keys(Object.keys(table).reduce(function (a, b) {
    return Object.assign(a, table[b]);
}, {}));
var llTable = Object.keys(table).map(function (a) {
    return a + ',' + tokens.map(function (b) {
        if (!table[a][b]) {
          return '';
        }

        var text = JSON.stringify(table[a][b]);
        return '"' + text.slice(1, text.length - 1) + '"';
    }).join(',');
});

console.log(',' + tokens.join(','));
console.log(llTable.join('\n'));
