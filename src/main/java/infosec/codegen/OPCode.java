package infosec.codegen;

public enum OPCode {
    OP_aaload (0x32),
    OP_aastore (0x53),
    OP_aconst_null (0x01),
    OP_aload (0x19),
    OP_aload_0 (0x2a),
    OP_aload_1 (0x2b),
    OP_aload_2 (0x2c),
    OP_aload_3 (0x2d),
    OP_anewarray (0xbd),
    OP_areturn (0xb0),
    OP_arraylength (0xbe),
    OP_astore (0x3a),
    OP_astore_0 (0x4b),
    OP_astore_1 (0x4c),
    OP_astore_2 (0x4d),
    OP_astore_3 (0x4e),
    OP_athrow (0xbf),
    OP_baload (0x33),
    OP_bastore (0x54),
    OP_bipush (0x10),
    OP_breakpoint (0xca),
    OP_caload (0x34),
    OP_castore (0x55),
    OP_checkcast (0xc0),
    OP_d2f (0x90),
    OP_d2i (0x8e),
    OP_d2l (0x8f),
    OP_dadd (0x63),
    OP_daload (0x31),
    OP_dastore (0x52),
    OP_dcmpg (0x98),
    OP_dcmpl (0x97),
    OP_dconst_0 (0x0e),
    OP_dconst_1 (0x0f),
    OP_ddiv (0x6f),
    OP_dload (0x18),
    OP_dload_0 (0x26),
    OP_dload_1 (0x27),
    OP_dload_2 (0x28),
    OP_dload_3 (0x29),
    OP_dmul (0x6b),
    OP_dneg (0x77),
    OP_drem (0x73),
    OP_dreturn (0xaf),
    OP_dstore (0x39),
    OP_dstore_0 (0x47),
    OP_dstore_1 (0x48),
    OP_dstore_2 (0x49),
    OP_dstore_3 (0x4a),
    OP_dsub (0x67),
    OP_dup (0x59),
    OP_dup_x1 (0x5a),
    OP_dup_x2 (0x5b),
    OP_dup2 (0x5c),
    OP_dup2_x1 (0x5d),
    OP_dup2_x2 (0x5e),
    OP_f2d (0x8d),
    OP_f2i (0x8b),
    OP_f2l (0x8c),
    OP_fadd (0x62),
    OP_faload (0x30),
    OP_fastore (0x51),
    OP_fcmpg (0x96),
    OP_fcmpl (0x95),
    OP_fconst_0 (0x0b),
    OP_fconst_1 (0x0c),
    OP_fconst_2 (0x0d),
    OP_fdiv (0x6e),
    OP_fload (0x17),
    OP_fload_0 (0x22),
    OP_fload_1 (0x23),
    OP_fload_2 (0x24),
    OP_fload_3 (0x25),
    OP_fmul (0x6a),
    OP_fneg (0x76),
    OP_frem (0x72),
    OP_freturn (0xae),
    OP_fstore (0x38),
    OP_fstore_0 (0x43),
    OP_fstore_1 (0x44),
    OP_fstore_2 (0x45),
    OP_fstore_3 (0x46),
    OP_fsub (0x66),
    OP_getfield (0xb4),
    OP_getstatic (0xb2),
    OP_goto (0xa7),
    OP_goto_w (0xc8),
    OP_i2b (0x91),
    OP_i2c (0x92),
    OP_i2d (0x87),
    OP_i2f (0x86),
    OP_i2l (0x85),
    OP_i2s (0x93),
    OP_iadd (0x60),
    OP_iaload (0x2e),
    OP_iand (0x7e),
    OP_iastore (0x4f),
    OP_iconst_m1 (0x02),
    OP_iconst_0 (0x03),
    OP_iconst_1 (0x04),
    OP_iconst_2 (0x05),
    OP_iconst_3 (0x06),
    OP_iconst_4 (0x07),
    OP_iconst_5 (0x08),
    OP_idiv (0x6c),
    OP_if_acmpeq (0xa5),
    OP_if_acmpne (0xa6),
    OP_if_icmpeq (0x9f),
    OP_if_icmpge (0xa2),
    OP_if_icmpgt (0xa3),
    OP_if_icmple (0xa4),
    OP_if_icmplt (0xa1),
    OP_if_icmpne (0xa0),
    OP_ifeq (0x99),
    OP_ifge (0x9c),
    OP_ifgt (0x9d),
    OP_ifle (0x9e),
    OP_iflt (0x9b),
    OP_ifne (0x9a),
    OP_ifnonnull (0xc7),
    OP_ifnull (0xc6),
    OP_iinc (0x84),
    OP_iload (0x15),
    OP_iload_0 (0x1a),
    OP_iload_1 (0x1b),
    OP_iload_2 (0x1c),
    OP_iload_3 (0x1d),
    OP_impdep1 (0xfe),
    OP_impdep2 (0xff),
    OP_imul (0x68),
    OP_ineg (0x74),
    OP_instanceof (0xc1),
    OP_invokedynamic (0xba),
    OP_invokeinterface (0xb9),
    OP_invokespecial (0xb7),
    OP_invokestatic (0xb8),
    OP_invokevirtual (0xb6),
    OP_ior (0x80),
    OP_irem (0x70),
    OP_ireturn (0xac),
    OP_ishl (0x78),
    OP_ishr (0x7a),
    OP_istore (0x36),
    OP_istore_0 (0x3b),
    OP_istore_1 (0x3c),
    OP_istore_2 (0x3d),
    OP_istore_3 (0x3e),
    OP_isub (0x64),
    OP_iushr (0x7c),
    OP_ixor (0x82),
    OP_jsr (0xa8),
    OP_jsr_w (0xc9),
    OP_l2d (0x8a),
    OP_l2f (0x89),
    OP_l2i (0x88),
    OP_ladd (0x61),
    OP_laload (0x2f),
    OP_land (0x7f),
    OP_lastore (0x50),
    OP_lcmp (0x94),
    OP_lconst_0 (0x09),
    OP_lconst_1 (0x0a),
    OP_ldc (0x12),
    OP_ldc_w (0x13),
    OP_ldc2_w (0x14),
    OP_ldiv (0x6d),
    OP_lload (0x16),
    OP_lload_0 (0x1e),
    OP_lload_1 (0x1f),
    OP_lload_2 (0x20),
    OP_lload_3 (0x21),
    OP_lmul (0x69),
    OP_lneg (0x75),
    OP_lookupswitch (0xab),
    OP_lor (0x81),
    OP_lrem (0x71),
    OP_lreturn (0xad),
    OP_lshl (0x79),
    OP_lshr (0x7b),
    OP_lstore (0x37),
    OP_lstore_0 (0x3f),
    OP_lstore_1 (0x40),
    OP_lstore_2 (0x41),
    OP_lstore_3 (0x42),
    OP_lsub (0x65),
    OP_lushr (0x7d),
    OP_lxor (0x83),
    OP_monitorenter (0xc2),
    OP_monitorexit (0xc3),
    OP_multianewarray (0xc5),
    OP_new (0xbb),
    OP_newarray (0xbc),
    OP_nop (0x00),
    OP_pop (0x57),
    OP_pop2 (0x58),
    OP_putfield (0xb5),
    OP_putstatic (0xb3),
    OP_ret (0xa9),
    OP_return (0xb1),
    OP_saload (0x35),
    OP_sastore (0x56),
    OP_sipush (0x11),
    OP_swap (0x5f),
    OP_tableswitch (0xaa),
    OP_wide (0xc4);


    private final int opcode;

    OPCode(int opcode) {
        this.opcode = opcode;
    }

    public int getOP() {
        return this.opcode;
    }
}
