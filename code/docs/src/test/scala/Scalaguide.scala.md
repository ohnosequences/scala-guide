
```scala
// package ohnosequences.scalaguide.test

// import org.scalatest.FunSuite

// import ohnosequences.pointless._, AnyRecord._, AnyProperty._, AnyTypeSet._

// object Test {

//   object k01 extends Property[Int]; object k02 extends Property[Int]; object k03 extends Property[Int]; object k04 extends Property[Int]; object k05 extends Property[Int]; object k06 extends Property[Int]; object k07 extends Property[Int]; object k08 extends Property[Int];
//   object k09 extends Property[Int]; object k10 extends Property[Int]; object k11 extends Property[Int]; object k12 extends Property[Int]; object k13 extends Property[Int]; object k14 extends Property[Int]; object k15 extends Property[Int]; object k16 extends Property[Int];
//   object k17 extends Property[Int]; object k18 extends Property[Int]; object k19 extends Property[Int]; object k20 extends Property[Int]; object k21 extends Property[Int]; object k22 extends Property[Int]; object k23 extends Property[Int]; object k24 extends Property[Int];
//   object k25 extends Property[Int]; object k26 extends Property[Int]; object k27 extends Property[Int]; object k28 extends Property[Int]; object k29 extends Property[Int]; object k30 extends Property[Int]; object k31 extends Property[Int]; object k32 extends Property[Int];
//   object k33 extends Property[Int]; object k34 extends Property[Int]; object k35 extends Property[Int];

//   object record extends Record[
//     k01.type :~: k02.type :~: k03.type :~: k04.type :~: k05.type :~:
//     k06.type :~: k07.type :~: k08.type :~: k09.type :~: k10.type :~:
//     k11.type :~: k12.type :~: k13.type :~: k14.type :~: k15.type :~:
//     k16.type :~: k17.type :~: k18.type :~: k19.type :~: k20.type :~:
//     k21.type :~: k22.type :~: k23.type :~: k24.type :~: k25.type :~:
//     k26.type :~: k27.type :~: k28.type :~: k29.type :~: k30.type :~:
//     k31.type :~: k32.type :~: k33.type :~: k34.type :~: k35.type :~: ∅,
//     ValueOf[k01.type] :~: ValueOf[k02.type] :~: ValueOf[k03.type] :~: ValueOf[k04.type] :~: ValueOf[k05.type] :~:
//     ValueOf[k06.type] :~: ValueOf[k07.type] :~: ValueOf[k08.type] :~: ValueOf[k09.type] :~: ValueOf[k10.type] :~:
//     ValueOf[k11.type] :~: ValueOf[k12.type] :~: ValueOf[k13.type] :~: ValueOf[k14.type] :~: ValueOf[k15.type] :~:
//     ValueOf[k16.type] :~: ValueOf[k17.type] :~: ValueOf[k18.type] :~: ValueOf[k19.type] :~: ValueOf[k20.type] :~:
//     ValueOf[k21.type] :~: ValueOf[k22.type] :~: ValueOf[k23.type] :~: ValueOf[k24.type] :~: ValueOf[k25.type] :~: 
//     ValueOf[k26.type] :~: ValueOf[k27.type] :~: ValueOf[k28.type] :~: ValueOf[k29.type] :~: ValueOf[k30.type] :~:   
//     ValueOf[k31.type] :~: ValueOf[k32.type] :~: ValueOf[k33.type] :~: ValueOf[k34.type] :~: ValueOf[k35.type] :~: ∅

//         // k11  :~: k12  :~: k13  :~: k14  :~: k15  :~:    // 9s
//     // 
//   ](
//     k01 :~: k02 :~: k03 :~: k04 :~: k05 :~: 
//     k06 :~: k07 :~: k08 :~: k09 :~: k10 :~: 
//     k11 :~: k12 :~: k13 :~: k14 :~: k15 :~:   // 6s
//     k16 :~: k17 :~: k18 :~: k19 :~: k20 :~:   // 14s
//     k21 :~: k22 :~: k23 :~: k24 :~: k25 :~:   // 29s
//     k26 :~: k27 :~: k28 :~: k29 :~: k30 :~:
//     k31 :~: k32 :~: k33 :~: k34 :~: k35 :~: ∅
//   )

//   val r = record entry (

//     (k01(1)) :~: (k02(1)) :~: (k03(1)) :~: (k04(1)) :~: (k05(1)) :~: 
//     (k06(1)) :~: (k07(1)) :~: (k08(1)) :~: (k09(1)) :~: (k10(1)) :~:
//     (k11(1)) :~: (k12(1)) :~: (k13(1)) :~: (k14(1)) :~: (k15(1)) :~:
//     (k16(1)) :~: (k17(1)) :~: (k18(1)) :~: (k19(1)) :~: (k20(1)) :~:  
//     (k21(1)) :~: (k22(1)) :~: (k23(1)) :~: (k24(1)) :~: (k25(1)) :~: 
//     (k26(1)) :~: (k27(1)) :~: (k28(1)) :~: (k29(1)) :~: (k30(1)) :~:
//     (k31(1)) :~: (k32(1)) :~: (k33(1)) :~: (k34(1)) :~: (k35(1)) :~: ∅
//   )

//   // val r = record fields (

//   //   (k01(1)) :~: (k02(1)) :~: (k03(1)) :~: (k04(1)) :~: (k05(1)) :~:
//   //   (k06(1)) :~: (k07(1)) :~: (k08(1)) :~: (k09(1)) :~: (k10(1)) :~:
//   //   (k11(1)) :~: (k12(1)) :~: (k13(1)) :~: (k14(1)) :~: (k15(1)) :~:
//   //   (k16(1)) :~: (k17(1)) :~: (k18(1)) :~: (k19(1)) :~: (k20(1)) :~: ∅
//   //   // (k21(1)) :~: (k22(1)) :~: (k23(1)) :~: (k24(1)) :~: (k25(1)) :~: ∅
//   // //   (k26(1)) :~: (k27(1)) :~: (k28(1)) :~: (k29(1)) :~: (k30(1)) :~:
//   // //   (k31(1)) :~: (k32(1)) :~: (k33(1)) :~: (k34(1)) :~: (k35(1)) :~: ∅
//   // )
// }


// class ScalaguideTest extends FunSuite {

  

//   test("Record Compilation times") {


//     import Test._

//     // r get k01
//     // r get k02
//     // r get k03
//     // r get k04
//     // r get k05
//     // r get k10
//     // r get k20
//   }
// }
//  
```


------

### Index

+ src
  + test
    + scala
      + [Scalaguide.scala][test/scala/Scalaguide.scala]
      + [errors.scala][test/scala/errors.scala]
  + main
    + scala
      + [override.scala][main/scala/override.scala]
      + [typeMembers.scala][main/scala/typeMembers.scala]
      + [taggedTypes.scala][main/scala/taggedTypes.scala]
      + [refinementsAndWith.scala][main/scala/refinementsAndWith.scala]
      + [existentials.scala][main/scala/existentials.scala]
      + [Scalaguide.scala][main/scala/Scalaguide.scala]
      + [errors.scala][main/scala/errors.scala]

[test/scala/Scalaguide.scala]: Scalaguide.scala.md
[test/scala/errors.scala]: errors.scala.md
[main/scala/override.scala]: ../../main/scala/override.scala.md
[main/scala/typeMembers.scala]: ../../main/scala/typeMembers.scala.md
[main/scala/taggedTypes.scala]: ../../main/scala/taggedTypes.scala.md
[main/scala/refinementsAndWith.scala]: ../../main/scala/refinementsAndWith.scala.md
[main/scala/existentials.scala]: ../../main/scala/existentials.scala.md
[main/scala/Scalaguide.scala]: ../../main/scala/Scalaguide.scala.md
[main/scala/errors.scala]: ../../main/scala/errors.scala.md