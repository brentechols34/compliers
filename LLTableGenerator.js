var arr = 'SystemGoal: Program MP_EOF\nProgram: ProgramHeading MP_SCOLON Block MP_PERIOD\nProgramHeading: MP_PROGRAM ProgramIdentifier\nBlock: VariableDeclarationPart ProcedureAndFunctionDeclarationPart StatementPart\nVariableDeclarationPart: MP_VAR VariableDeclaration MP_SCOLON VariableDeclarationTail\nVariableDeclarationPart: lambda\nVariableDeclarationTail: VariableDeclaration MP_SCOLON VariableDeclarationTail\nVariableDeclarationTail: lambda\nVariableDeclaration: IdentifierList MP_COLON Type\nType: MP_INTEGER\nType: MP_FLOAT\nType: MP_STRING\nType: MP_BOOLEAN\nProcedureAndFunctionDeclarationPart: ProcedureDeclaration ProcedureAndFunctionDeclarationPart\nProcedureAndFunctionDeclarationPart: FunctionDeclaration ProcedureAndFunctionDeclarationPart\nProcedureAndFunctionDeclarationPart: lambda\nProcedureDeclaration: ProcedureHeading MP_SCOLON Block MP_SCOLON\nFunctionDeclaration: FunctionHeading MP_SCOLON Block MP_SCOLON\nProcedureHeading: MP_PROCEDURE ProcedureIdentifier OptionalFormalParameterList\nFunctionHeading: MP_FUNCTION FunctionIdentifier OptionalFormalParameterList MP_COLON Type\nOptionalFormalParameterList: MP_LPAREN FormalParameterSection FormalParameterSectionTail MP_RPAREN\nOptionalFormalParameterList: lambda\nFormalParameterSectionTail: MP_SCOLON FormalParameterSection FormalParameterSectionTail\nFormalParameterSectionTail: lambda\nFormalParameterSection: ValueParameterSection\nFormalParameterSection: VariableParameterSection\nValueParameterSection: IdentifierList MP_COLON Type\nVariableParameterSection: MP_VAR IdentifierList MP_COLON Type\nStatementPart: CompoundStatement\nCompoundStatement: MP_BEGIN StatementSequence MP_END\nStatementSequence: Statement StatementTail\nStatementTail: MP_SCOLON Statement StatementTail\nStatementTail: lambda\nStatement: EmptyStatement\nStatement: CompoundStatement\nStatement: ReadStatement\nStatement: WriteStatement\nStatement: AssignmentStatement\nStatement: IfStatement\nStatement: WhileStatement\nStatement: RepeatStatement\nStatement: ForStatement\nStatement: ProcedureStatement\nEmptyStatement: lambda\nReadStatement: MP_READ MP_LPAREN ReadParameter ReadParameterTail MP_RPAREN\nReadParameterTail: MP_COMMA ReadParameter ReadParameterTail\nReadParameterTail: lambda\nReadParameter: VariableIdentifier\nWriteStatement: MP_WRITE MP_LPAREN WriteParameter WriteParameterTail MP_RPAREN\nWriteStatement: MP_WRITELN MP_LPAREN WriteParameter WriteParameterTail MP_RPAREN\nWriteParameterTail: MP_COMMA WriteParameter WriteParameterTail\nWriteParameterTail: lambda\nWriteParameter: OrdinalExpression\nAssignmentStatement: VariableIdentifier MP_ASSIGN Expression\nAssignmentStatement: FunctionIdentifier MP_ASSIGN Expression\nIfStatement: MP_IF BooleanExpression MP_THEN Statement OptionalElsePart\nOptionalElsePart: MP_ELSE Statement\nOptionalElsePart: lambda\nRepeatStatement: MP_REPEAT StatementSequence MP_UNTIL BooleanExpression\nWhileStatement: MP_WHILE BooleanExpression MP_DO Statement\nForStatement: MP_FOR ControlVariable MP_ASSIGN InitialValue StepValue FinalValue MP_DO Statement\nControlVariable: VariableIdentifier\nInitialValue: OrdinalExpression\nStepValue: MP_TO\nStepValue: MP_DOWNTO\nFinalValue: OrdinalExpression\nProcedureStatement: ProcedureIdentifier OptionalActualParameterList\nOptionalActualParameterList: MP_LPAREN ActualParameter ActualParameterTail MP_RPAREN\nOptionalActualParameterList: lambda\nActualParameterTail: MP_COMMA ActualParameter ActualParameterTail\nActualParameterTail: lambda\nActualParameter: OrdinalExpression\nExpression: SimpleExpression OptionalRelationalPart\nOptionalRelationalPart: RelationalOperator SimpleExpression\nOptionalRelationalPart: lambda\nRelationalOperator: MP_EQUAL\nRelationalOperator: MP_LTHAN\nRelationalOperator: MP_GTHAN\nRelationalOperator: MP_LEQUAL\nRelationalOperator: MP_GEQUAL\nRelationalOperator: MP_NEQUAL\nSimpleExpression: OptionalSign Term TermTail\nTermTail: AddingOperator Term TermTail\nTermTail: lambda\nOptionalSign: MP_PLUS\nOptionalSign: MP_MINUS\nOptionalSign: lambda\nAddingOperator: MP_PLUS\nAddingOperator: MP_MINUS\nAddingOperator: MP_OR\nTerm: Factor FactorTail\nFactorTail: MultiplyingOperator Factor FactorTail\nFactorTail: lambda\nMultiplyingOperator: MP_TIMES\nMultiplyingOperator: MP_FLOAT_DIVIDE\nMultiplyingOperator: MP_DIV\nMultiplyingOperator: MP_MOD\nMultiplyingOperator: MP_AND\nFactor: MP_INTEGER_LIT\nFactor: MP_FLOAT_LIT\nFactor: MP_STRING_LIT\nFactor: MP_TRUE\nFactor: MP_FALSE\nFactor: MP_NOT Factor\nFactor: MP_LPAREN Expression MP_RPAREN\nFactor: FunctionIdentifier OptionalActualParameterList\nProgramIdentifier: MP_IDENTIFIER\nVariableIdentifier: MP_IDENTIFIER\nProcedureIdentifier: MP_IDENTIFIER\nFunctionIdentifier: MP_IDENTIFIER\nBooleanExpression: Expression\nOrdinalExpression: Expression\nIdentifierList: MP_IDENTIFIER IdentifierTail\nIdentifierTail: MP_COMMA MP_IDENTIFIER IdentifierTail\nIdentifierTail: lambda\nFactor: VariableIdentifier'.split('\n');

var preceding = {};

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
  	if (end.trim() === 'lambda' || dict[end.split(' ')[1].trim()] === undefined) {
          dict[start][end.split(' ')[1].trim()] = [term.index];
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
                dict[start][b] = dict[start][b] ? union_arrays(dict[start][b], [term.index]) : [term.index];
            });
        });

    }
    
    if (!isEpsilon) {
        terms = terms.filter(function (a) { return a !== 'lambda' });
    }
    
    return { index: term.index, terminals: terms };
};

var generatePreceeding = function (term, dict) {
    var start = term.rule.split(':')[0];
    var end = term.rule.split(':')[1].split(' ').slice(1);
    
    for (var i = end.length - 1; i !== 0; i--) {
        if (dict[end[i-1]] && dict[end[i-1]]['lambda']) {
            preceding[end[i]] = preceding[end[i]] ? union_arrays(preceding[end[i]], [end[i-1]]) : [end[i-1]];
        }
    }
}

var applyEpsilon = function (term, application, dict, grammar) {
    var lambdaPointer = dict[term]['lambda'];
    if (dict[application]) {
        for (var key in dict[application]) {
            dict[term][key] = dict[term][key] ? union_arrays(dict[term][key], lambdaPointer) : lambdaPointer;
        }
    } else {
        dict[term][application] = dict[term][application] ? union_arrays(dict[term][application], lambdaPointer) : lambdaPointer;
    }

    // Propagate to preceding
    for (var key in preceding[term]) {
        if (dict[key] !== undefined) {
            applyEpsilon(preceding[term][key], application, dict, grammar);
        }
    }

    // Propagate to nested
    lambdaPointer.forEach(function (a) {
        var rule = grammar[a];
        end = rule.rule.split(':')[1].split(' ').slice(1);

        end.forEach(function (b) { if (dict[key] !== undefined) { applyEpsilon(b, application, dict, grammar); } });
    });
}
var epsilonPostProcess = function (term, dict, grammar) {
    var start = term.rule.split(':')[0];
    var end = term.rule.split(':')[1].split(' ').slice(1);
    
    for (var i = end.length - 1; i !== 0; i--) {
        if (dict[end[i-1]] && dict[end[i-1]]['lambda']) {
            applyEpsilon(end[i-1], end[i], dict, grammar);
        }
    }
}

var makeTable = function (arr) {
	var grammar = arr.map(function (a,b) { return { rule: a, index: b } });
	var lookup = grammar.reduce(function (a,b) { a[b.rule.split(':')[0]] = {}; return a; }, {});

    grammar.forEach(function (a) {
        makeTableRecurse(a, lookup, grammar);
    });
//    grammar.forEach(function (a) { generatePreceeding(a, lookup); });
//    grammar.forEach(function (a) { epsilonPostProcess(a, lookup, grammar); });
    var tokens = Object.keys(Object.keys(lookup).reduce(function (a, b) {
        return Object.assign(a, lookup[b]);
    }, {})).filter(function (a) { return a !== 'lambda'; });

    for (var key in lookup) {
        var lambda = lookup[key]['lambda'];
        if (lambda === undefined) {
            continue;
        }

        tokens.forEach(function (token) {
            lookup[key][token] = lookup[key][token] ? union_arrays(lookup[key][token], lambda) : lambda;
        });
    }

    return lookup;
};

debugger;
var table = makeTable(arr);
var tokens = Object.keys(Object.keys(table).reduce(function (a, b) {
    return Object.assign(a, table[b]);
}, {})).filter(function (a) { return a !== 'lambda'; });
var llTable = Object.keys(table).map(function (a) {
    return a + ',' + tokens.map(function (b) {
        if (!table[a][b]) {
          return '.';
        }

        var text = JSON.stringify(table[a][b]);
        return text.slice(1, text.length - 1).replace(/\,/g, '|') || '.';
    }).join(',');
});

console.log(',' + tokens.join(','));
console.log(llTable.join('\n'));
